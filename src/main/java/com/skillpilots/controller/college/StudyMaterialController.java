package com.skillpilots.controller.college;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.skillpilots.model.CollegeStudyMaterial;
import com.skillpilots.model.CompanyStudyMaterial;
import com.skillpilots.model.NotificationRequest;
import com.skillpilots.model.User;
import com.skillpilots.repository.CollegeStudyMaterialRepo;
import com.skillpilots.repository.NotificationRepository;
import com.skillpilots.repository.UserRepository;
import com.skillpilots.service.CompanyStudyMaterialService;
import com.skillpilots.service.ComponyService;
import com.skillpilots.utility.FirebaseService;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins = { "*" })
public class StudyMaterialController {
	@Autowired
	private CollegeStudyMaterialRepo materialRepository;
	@Autowired
	private HttpSession session;
	@Autowired
	private ComponyService componyService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private FirebaseService firebaseService;

	@Autowired
	private CompanyStudyMaterialService companyStudyMaterialService;
	private static final String UPLOAD_ = "src/main/resources/static/style/offer";

	@GetMapping("/study_material")
	public String getStudyMaterial(Model model) {
		 User user = (User) session.getAttribute("user");
		    List<CollegeStudyMaterial> materials = materialRepository.findByCollegeIdAndDeptIdOrderByStudyMaterialIdDesc(user.getCollege_id(), user.getDepartment());
		    model.addAttribute("materials", materials);
		    return "study_material";		
	}
	
	
	// upload study material college-side shrunkhal 2/25
	@PostMapping("/uploadMaterial")
	public String uploadMaterial(
	        @RequestParam("file") MultipartFile file,
	        @RequestParam("title") String title,
	        @RequestParam(value = "description", required = false) String description,
	        @RequestParam(value = "videourl", required = false) String videourl,
	        RedirectAttributes redirectAttributes) {

	    User user = (User) session.getAttribute("user");
	    try {
	        if (file.isEmpty()) {
	            redirectAttributes.addFlashAttribute("message", "File is empty!");
	            return "redirect:/study_material";
	        }

	        CollegeStudyMaterial collegeStudyMaterial = new CollegeStudyMaterial();
	        String uploadDirectory = (System.getenv("ENV") != null && System.getenv("ENV").equalsIgnoreCase("LIVE")) 
	                                 ? "/var/www/html/study-material" : "uploads";

	        Path uploadPath = Paths.get(uploadDirectory).toAbsolutePath().normalize();
	        Files.createDirectories(uploadPath);

	        String originalFileName = file.getOriginalFilename();
	        if (originalFileName == null || originalFileName.isEmpty()) {
	            redirectAttributes.addFlashAttribute("message", "Invalid file name!");
	            return "redirect:/study_material";
	        }
	        String sanitizedFileName = originalFileName.replaceAll("\\s+", "_");

	        Path filePath = uploadPath.resolve(sanitizedFileName);
	        int counter = 1;
	        while (Files.exists(filePath)) {
	            String baseName = sanitizedFileName.contains(".")
	                    ? sanitizedFileName.substring(0, sanitizedFileName.lastIndexOf('.'))
	                    : sanitizedFileName;
	            String extension = sanitizedFileName.contains(".")
	                    ? sanitizedFileName.substring(sanitizedFileName.lastIndexOf('.'))
	                    : "";
	            sanitizedFileName = baseName + "_" + counter + extension;
	            filePath = uploadPath.resolve(sanitizedFileName);
	            counter++;
	        }

	        file.transferTo(filePath.toFile());

	        collegeStudyMaterial.setTopic(title);
	        collegeStudyMaterial.setFileName(originalFileName);
	        collegeStudyMaterial.setUploadUrl(sanitizedFileName);
	        collegeStudyMaterial.setCollegeId(user.getCollege_id());
	        collegeStudyMaterial.setDeptId(user.getDepartment());
	        collegeStudyMaterial.setDescription(description);

	        if (videourl != null && !videourl.trim().isEmpty()) {
	            collegeStudyMaterial.setVideourl(videourl);
	        }

	        materialRepository.save(collegeStudyMaterial);

	        // ** Notification to student 03/04/25 shrunkhal**
	        List<User> students = userRepository.getStudentByDeptAndCollege(user.getDepartment(), user.getCollege_id());

	        List<NotificationRequest> notifications = new ArrayList<>();
	        for (User student : students) {
	            NotificationRequest notificationRequest = new NotificationRequest();
	            notificationRequest.setMsgBody("Study Material Posted");
	            notificationRequest.setTitle("Study");
	            notificationRequest.setReceiverId(student.getId());
	            notificationRequest.setDateTime(LocalDateTime.now());
	            notifications.add(notificationRequest);
	        }

	        notificationRepository.saveAll(notifications);

	        CompletableFuture.runAsync(() -> {
	            for (User student : students) {
	                if (student.getNotificationToken() != null && !student.getNotificationToken().isEmpty()) {
	                    firebaseService.sendNotification(student.getNotificationToken(), "New Study Material", "Check the latest study material uploaded.");
	                }
	            }
	        });

	        redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");
	        return "redirect:/study_material";

	    } catch (IOException e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("message", "File upload failed: " + e.getMessage());
	        return "redirect:/study_material";
	    }
	}


	@GetMapping("/uploads/{fileName:.+}")
	public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
		 String baseDirectory;
		    if (System.getenv("ENV") != null && System.getenv("ENV").equalsIgnoreCase("LIVE")) {
		     
		        baseDirectory = "/var/www/html/study-material";
		    } else {
		        baseDirectory = "uploads";
		    }
		
		Path filePath = Paths.get(baseDirectory).resolve(fileName).normalize();
	    Resource resource = null;
		try {
			resource = new UrlResource(filePath.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	    if (!resource.exists()) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
	    }
	    return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
	            .body(resource);
	}
	@DeleteMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> deleteFile(@RequestParam String fileName,@RequestParam Long id) {
		 String baseDirectory;
		    if (System.getenv("ENV") != null && System.getenv("ENV").equalsIgnoreCase("LIVE")) {
		        baseDirectory = "/var/www/html/study-material";
		    } else {
		        baseDirectory = "uploads";
		    }
		Path filePath = Paths.get(baseDirectory).resolve(fileName).normalize();
	    try {
	        Files.delete(filePath); 
	        materialRepository.deleteById(id);
	        Map<String, Object> response = new HashMap<>();
	        
	        response.put("success", true);
	        return ResponseEntity.ok(response);
	    } catch (IOException e) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", false);
	        response.put("error", "Error deleting file: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	
	
	
	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
	   
		 String baseDirectory;
		    if (System.getenv("ENV") != null && System.getenv("ENV").equalsIgnoreCase("LIVE")) {
		     
		        baseDirectory = "/var/www/html/study-material";
		    } else {
		        baseDirectory = "uploads";
		    }
		
		Path filePath = Paths.get(baseDirectory).resolve(fileName).normalize();
	    Resource resource;
	    try {
	        resource = new UrlResource(filePath.toUri());
	        if (resource.exists() && resource.isReadable()) {
	            return ResponseEntity.ok()
	                    .contentType(MediaType.APPLICATION_PDF)
	                    .body(resource);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().build();
	    }
	}

	
	@GetMapping("/company_study_material")
	public String getCompanyStudyMaterial(Model model)
	{
		List<CompanyStudyMaterial> materials=null;
		User user =(User)session.getAttribute("user");

		if(user.getRole().equals("0")|| user.getRole().equals("10")) {
		 materials = companyStudyMaterialService.getAllStudyMaterial();
	
		}else {
		    Long company_id = componyService.findByEmail(user.getEmailIdCommon());

		    materials=	companyStudyMaterialService.getMaterialByCompany(company_id);
		}
		
	    model.addAttribute("materials", materials);
		return "company_study_material";
	}
	@GetMapping("/view_company")
	public String getCompanyStudentMaterial(Model model)
	{
		User user =(User)session.getAttribute("user");
		
		List<CompanyStudyMaterial> materials = companyStudyMaterialService.getAllStudyMaterial();
	    model.addAttribute("materials", materials);
		return "view_company";
	}
		
		/*
		  @GetMapping("/company_study_material") public String
		  getCompanyStudyMaterial(Model model) { User user = (User)
		  session.getAttribute("user"); List<CollegeStudyMaterial> materials =
		  companyStudyMaterialService.getStudyMaterialsByCompanyId(companyId);
		  model.addAttribute("materials", materials); return "company_study_material";
		  }
		 */
		
	@PostMapping("/uploadStudyMaterial")
	public String companyUploadStudyMaterial(
	        @RequestParam("file") MultipartFile file,
	        @RequestParam("title") String title,
	        @RequestParam(value = "description", required = false) String description,
	        @RequestParam(value= "videourl",required = false) String videourl,
	        HttpSession session) {
	    User user = (User) session.getAttribute("user");
	    
	    Long company_id = componyService.findByEmail(user.getEmailIdCommon());
	    try {
	        if (file.isEmpty()) {
	            session.setAttribute("message", "File is empty!");
	            return "redirect:/company_study_material";
	        }
	        CompanyStudyMaterial companyStudyMaterial = new CompanyStudyMaterial();
	        String uploadDirectory;
	        if (System.getenv("ENV") != null && System.getenv("ENV").equalsIgnoreCase("LIVE")) {
	            uploadDirectory = "/var/www/html/study-material";
	        } else {
	            uploadDirectory = "uploads";
	        }
	        Path uploadPath = Paths.get(uploadDirectory).toAbsolutePath().normalize();
	        Files.createDirectories(uploadPath);
	        String originalFileName = file.getOriginalFilename();
	        if (originalFileName == null || originalFileName.isEmpty()) {
	            session.setAttribute("message", "Invalid file name!");
	            return "redirect:/company_study_material";
	        }
	        String sanitizedFileName = originalFileName.replaceAll("\\s+", "_");
	        Path filePath = uploadPath.resolve(sanitizedFileName);
	        int counter = 1;
	        while (Files.exists(filePath)) {
	            String baseName = sanitizedFileName.contains(".")
	                    ? sanitizedFileName.substring(0, sanitizedFileName.lastIndexOf('.'))
	                    : sanitizedFileName;
	            String extension = sanitizedFileName.contains(".")
	                    ? sanitizedFileName.substring(sanitizedFileName.lastIndexOf('.'))
	                    : "";
	            sanitizedFileName = baseName + "_" + counter + extension;
	            filePath = uploadPath.resolve(sanitizedFileName);
	            counter++;
	        }
	        file.transferTo(filePath.toFile());
	        System.out.println("File successfully saved at: " + filePath.toString());
	        companyStudyMaterial.setTopic(title);
	        companyStudyMaterial.setFileName(originalFileName);
	        companyStudyMaterial.setUploadUrl(sanitizedFileName);
	        companyStudyMaterial.setDescription(description);
	        companyStudyMaterial.setCompanyId(company_id);
	        
	        if (videourl != null && !videourl.trim().isEmpty()) {
	            companyStudyMaterial.setVideourl(videourl);
	        }
	        companyStudyMaterialService.saveMaterial(companyStudyMaterial);
	        session.setAttribute("message", "File uploaded successfully!");
	        return "redirect:/company_study_material";
	    } catch (IOException e) {
	        e.printStackTrace(); 
	        session.setAttribute("message", "File upload failed: " + e.getMessage());
	        return "redirect:/company_study_material"; 
	    }
	}
	@DeleteMapping("/deleteStudyFile")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> deleteStudyMaterial(@RequestParam String fileName, @RequestParam Long id) {
	    String baseDirectory;
	    if (System.getenv("ENV") != null && System.getenv("ENV").equalsIgnoreCase("LIVE")) {
	        baseDirectory = "/var/www/html/study-material"; 
	    } else {
	        baseDirectory = "uploads"; 
	    }
	    Path filePath = Paths.get(baseDirectory, fileName).normalize();
	    try {
	        Files.delete(filePath);
	        companyStudyMaterialService.deleteMaterialById(id);
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", true);
	        return ResponseEntity.ok(response);
	    } catch (IOException e) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", false);
	        response.put("error", "Error deleting file: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	

	
}
