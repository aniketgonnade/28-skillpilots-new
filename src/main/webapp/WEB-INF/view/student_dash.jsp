<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Student Dashboard</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
  <style>
    body {
      font-family: 'Poppins', sans-serif;
      background: linear-gradient(to right, #f8f9fa, #e0e0e0);
      margin: 0;
      padding: 20px;
      color: #333;
    }
    .container {
      max-width: 1200px;
      margin: auto;
    }
    .top-section {
      display: flex;
      justify-content: space-between;
      align-items: center;
      background: linear-gradient(to right, #5e60ce, #7400b8);
      padding: 30px;
      border-radius: 20px;
      color: white;
      margin-bottom: 20px;
    }
    .greeting h2 {
      margin: 0;
      font-size: 28px;
    }
    .greeting p {
      font-size: 16px;
      margin: 5px 0;
    }
    .avatar {
      width: 70px;
      height: 70px;
      border-radius: 50%;
      background: url('https://i.ibb.co/xgKzrP6/avatar.png') center/cover no-repeat;
      margin-right: 15px;
    }
    .profile-card {
      display: flex;
      align-items: center;
    }
    .visit-btn {
      margin-top: 10px;
      background: #fff;
      color: #5e60ce;
      padding: 8px 15px;
      border: none;
      border-radius: 12px;
      cursor: pointer;
      font-weight: 600;
    }

    .grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(330px, 1fr));
      gap: 20px;
      margin-bottom: 20px;
    }
    .card {
      background: rgba(255, 255, 255, 0.6);
      backdrop-filter: blur(12px);
      border-radius: 20px;
      padding: 20px;
      box-shadow: 0 10px 30px rgba(0,0,0,0.1);
    }
    .card h3 {
      margin-bottom: 15px;
      font-size: 20px;
      color: #5e60ce;
    }
    .assessment-table {
      width: 100%;
      border-collapse: collapse;
    }
    .assessment-table th, .assessment-table td {
      padding: 10px;
      text-align: left;
    }
    .assessment-table th {
      background-color: #f0f0f0;
      font-weight: 600;
    }
    .job-circle {
      width: 60px;
      height: 60px;
      border-radius: 50%;
      background: #5e60ce;
      color: white;
      font-size: 24px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin: 10px 0;
    }
    .course {
      margin-bottom: 15px;
    }
    .progress-bar {
      background: #ddd;
      border-radius: 50px;
      height: 14px;
      width: 100%;
      overflow: hidden;
    }
    .progress {
      height: 100%;
      border-radius: 50px;
      transition: width 1s ease-in-out;
    }
    .uiux { background: #ff6b6b; }
    .frontend { background: #4dabf7; }
    .python { background: #38d9a9; }

    .date {
      background-color: white;
      color: #5e60ce;
      padding: 5px 15px;
      border-radius: 12px;
      display: inline-block;
      font-weight: bold;
      font-size: 14px;
      margin-bottom: 10px;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="top-section">
    <div>
      <div class="date">${date}</div>
      <div class="greeting">
        <h2>Hey Good Day ${user.name}</h2>
        <p><i class="fas fa-graduation-cap"></i> Education is not preparation for life; education is life itself.</p>
      </div>
    </div>
    <div class="profile-card">
      <div class="avatar"></div>
      <div>
        <div><strong>${user.name}</strong><br>Front-End Developer crafting clean, responsive websites. <a href="#" style="color: #fff;">Read More</a></div>
        <button class="visit-btn">Visit Profiles</button>
      </div>
    </div>
  </div>

  <div class="grid">
    <div class="card">
      <h3>ASSESSMENT</h3>
      <table class="assessment-table">
        <tr><th>Test Name</th><th>Levels</th><th>Scores</th></tr>
        <c:forEach var="a" items="${assessments}">
          <tr>
            <td>${a.test}</td>
            <td>${a.level}</td>
            <td>${a.score}</td>
          </tr>
        </c:forEach>
      </table>
    </div>

    <div class="card">
      <h3>INTERNSHIP</h3>
      <p><i class="fas fa-briefcase"></i> Completed Internships: <strong>${internshipsCompleted}</strong></p>
    </div>

    <div class="card">
      <h3>JOB</h3>
      <div class="job-circle">${acceptedJobs}</div>
      <p><i class="fas fa-file-alt"></i> Applications: ${jobApplications}</p>
      <p><i class="fas fa-check-circle"></i> Accepted: ${acceptedJobs}</p>
      <p><strong>Job Apply:</strong> ${acceptedJobs}/${jobApplications}</p>
    </div>
  </div>

  <div class="grid">
    <div class="card" style="grid-column: span 2;">
      <h3>COURSE STATUS</h3>
      <div class="course">
        <label>UI&UX Designer - ${courseProgress.UIUX}%</label>
        <div class="progress-bar"><div class="progress uiux" style="width:${courseProgress.UIUX}%"></div></div>
      </div>
      <div class="course">
        <label>Frontend Development - ${courseProgress.Frontend}%</label>
        <div class="progress-bar"><div class="progress frontend" style="width:${courseProgress.Frontend}%"></div></div>
      </div>
      <div class="course">
        <label>Python - ${courseProgress.Python}%</label>
        <div class="progress-bar"><div class="progress python" style="width:${courseProgress.Python}%"></div></div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
