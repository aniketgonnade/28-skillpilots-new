<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="user_session.jsp"%>
<link
	href="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css' />"
	rel="stylesheet">

<style>
.header-right {
	display: -webkit-box;
	display: flex;
	-webkit-box-pack: end;
	justify-content: flex-end;
	height: 70px;
	width: 70%;
}

.header-left {
	display: -webkit-box;
	display: flex;
	-webkit-box-align: center;
	align-items: center;
	height: 100%;
	width: 10%;
}

.notification-icon-container {
	position: relative;
	display: inline-block;
}

.notification-icon-container .badge {
	position: absolute;
	top: -8px;
	right: -10px;
	background-color: red;
	color: white;
	font-size: 12px;
	border-radius: 50%;
	padding: 3px 6px;
}

.notification-icon-container i {
	font-size: 24px;
	color: #555;
	cursor: pointer;
	transition: color 0.3s ease;
}

.notification-icon-container i:hover {
	color: #000;
}

/* Example CSS for modal */
/* Modal Styling */
/* Modal Styling */
#notificationModal {
    margin-left:1200px;
    margin-top:100px;
    margin-bottom: 900px;
    bottom: 20px; /* Distance from the bottom of the screen */
    right: 20px;  /* Distance from the right edge of the screen */
    width: 300px; /* Width of the modal */
    background-color: #fff;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
    border-radius: 8px;
    min-height: 200px;
    max-height:400px;

    overflow: auto;
    display: none; /* Initially hidden */
    z-index: 1000; /* Ensure it appears above other elements */
    animation: fadeIn 0.3s ease-in-out;
}

/* Fade-In Animation */
@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(20px); /* Slight slide-up effect */
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* Modal Content */
#notificationModal .modal-content {
    padding: 10px;
    max-height: 400px; /* Limit height with scroll */
    overflow-y: auto;
    box-sizing: border-box;
}

/* Close Button */
.close {
    float: right;
    font-size: 16px;
    cursor: pointer;
    color: #333;
}

/* Notification List */
.notification-list {
    list-style-type: none;
    padding: 0;
    margin: 0;
}

.notification-list li {
    padding: 12px;
    border-bottom: 1px solid #ccc;
    cursor: pointer;
    border-radius: 6px;
    transition: background-color 0.3s ease;
}

.notification-list li.unread {
    background-color: #f0f8ff; /* Light blue for unread */
    font-weight: bold;
}

.notification-list li.read {
    background-color: #fff; /* White for read */
    font-weight: normal;
}

.notification-list li:hover {
    background-color: #f5f5f5; /* Light gray on hover */
}


</style>
<div class="header">
	<div class="header-left">
		<div class="menu-icon dw dw-menu"></div>
		<div class="search-toggle-icon dw dw-search2"
			data-toggle="header_search"></div>
	</div>
	<div class="header-right">
		
		<div class="dashboard-setting user-notification">
			<div class="dropdown">
				<a class="dropdown-toggle no-arrow" href="javascript:;"
					data-toggle="right-sidebar"> <i class="dw dw-settings2"></i>
				</a>
			</div>
		</div>

		<!-- Notification Bell Icon Section -->
		<div class="user-notification">
			<div class="dropdown">
				<div class="notification-icon-container"
					style="position: relative; display: inline-; margin-right: 20px;">
					<!-- Bell Icon -->
					<i class="fas fa-bell"
						style="font-size: 24px; color: white; cursor: pointer;"
						onclick="toggleNotifications()"></i>

					<!-- Notification Count Badge -->
					<span id="notificationCount" class="badge"
						style="position: absolute; top: -5px; right: -10px; background-color: red; color: white; border-radius: 50%; padding: 3px 6px; font-size: 12px; display: none;">
						0 </span>
				</div>
			</div>
		</div>

		<!-- Notification Modal -->
		<div id="notificationModal" class="modal" style="display: none;">
			<div class="modal-content">
				<span class="close" onclick="toggleNotifications()">&times;</span>
				<ul id="notificationList" class="notification-list"></ul>
				<!-- Notification list will be populated here -->
			</div>
		</div>


		<%-- 	<div class="user-notification">
				<div class="dropdown">
					<a class="dropdown-toggle no-arrow" href="#" role="button" data-toggle="dropdown">
						<i class="icon-copy dw dw-notification"></i>
						<span class="badge notification-active">
						<c:if
							test="${notifResponse.urCount gt 0}">
							<span class="label label-warning" id="noOfNotif"
								style="font-size: 13px !important;color: #9ff711;">${notifResponse.urCount}</span>
						</c:if>
						</span>
					</a>
				<div class="dropdown-menu dropdown-menu-right">
					<!-- Header Section -->

				</div>
			</div>
			</div> --%>
		<div class="user-info-dropdown">
			<div class="dropdown">
				<a class="dropdown-toggle" href="#" role="button"
					data-toggle="dropdown"> <span class="user-icon"> <img
						src="<c:url value='/get_logo'/>" alt="Custom Logo"
						style="width: 100%; height: 100%;">

				</span> <span class="user-name"> <c:if
							test="${not empty user.email}">${user.username}</c:if>
				</span>
				</a>

				<div
					class="dropdown-menu dropdown-menu-right dropdown-menu-icon-list">
					<c:if test="${user.role eq '0' || user.role eq '10'}">
						<a class="dropdown-item"
							href="${pageContext.request.contextPath}/editStudentProfile"><i
							class="dw dw-user1"></i> Edit Profile</a>
					</c:if>

					<c:if test="${user.role eq '5'}">
						<a class="dropdown-item"
							href="${pageContext.request.contextPath}/editCollegeProfile"><i
							class="dw dw-user1"></i> Edit Profile</a>
						<a class="dropdown-item"
							href="${pageContext.request.contextPath}/editUserProfile"><i
							class="dw dw-user1"></i> Edit III Profile</a>

					</c:if>

					<c:if test="${user.role eq '1'}">
						<a class="dropdown-item"
							href="${pageContext.request.contextPath}/editCompProfile"><i
							class="dw dw-user1"></i> Edit Profile</a>
						<a class="dropdown-item"
							href="${pageContext.request.contextPath}/editUserProfile"><i
							class="dw dw-user1"></i> Edit HR Profile</a>

					</c:if>
					<c:if
						test="${user.role eq '7' || user.role eq '8' || user.role eq '6'|| user.role eq '4' || user.role eq '3' || user.role eq '2'}">


						<a class="dropdown-item"
							href="${pageContext.request.contextPath}/editUserProfile"><i
							class="dw dw-user1"></i> Edit Profile</a>

					</c:if>
					<c:if test="${user_data.role eq '2'}">
						<a class="dropdown-item"
							href="${pageContext.request.contextPath}/editUserProfile"><i
							class="dw dw-user1"></i> Edit Profile</a>
					</c:if>
					<c:if test="${user_data.role eq '3'}">
						<a class="dropdown-item"
							href="${pageContext.request.contextPath}/editUserProfile"><i
							class="dw dw-user1"></i> Edit Profile</a>
					</c:if>
					<a class="dropdown-item" href="change_password"><i
						class="dw dw-password"></i> Change Password</a> <a
						class="dropdown-item"
						href="${pageContext.request.contextPath}/logout"><i
						class="dw dw-logout"></i> Log Out</a>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	
let notifications = []; // Store notifications array

// Fetch notifications based on user ID
// Polling interval (e.g., 10 seconds)

function fetchNotifications() {
    const userId = ${user.id}; // Assuming the user ID is available
    const url = "${pageContext.request.contextPath}/notifications?id=" + userId;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch notifications");
            }
            return response.json();
        })
        .then(data => {
            notifications = data;
            updateNotificationCount();
            populateNotifications();
        })
        .catch(error => {
            console.error("Error fetching notifications:", error);
            document.getElementById("notificationList").innerHTML =
                "<li style='color: red;'>Failed to load notifications.</li>";
        });

    // Repeat the fetch every POLL_INTERVAL
}


function updateNotificationCount() {
    const unreadCount = notifications.filter(n => n.status === false).length;
    const badge = document.getElementById('notificationCount');
    if (unreadCount > 0) {
        badge.innerText = unreadCount;
        badge.style.display = 'inline';
    } else {
        badge.style.display = 'none';
    }
}


function populateNotifications() {
    const notificationList = document.getElementById('notificationList');
    
    if (notifications.length === 0) {
        notificationList.innerHTML = "<li>No new notifications</li>";
    } else {
        console.log("Populating Notifications"); // Log when function is called
        let notificationsHTML = '';
        notifications.forEach(n => {
            console.log("Notification:", n); // Log each notification object
            const date = new Date(n.dateTime);
            const formattedDate = date.getDate() + '-' + (date.getMonth() + 1) + '-' + date.getFullYear() + ' ' + date.getHours() + ':' + date.getMinutes();
            notificationsHTML +=
                '<li onclick="markAsRead(' + n.notifId + ')" class="' + (n.read ? '' : 'unread') + '">' +
                '<strong>' + n.title + '</strong><br>' +
                n.msgBody + '<br>' +
                '<small>' + formattedDate + '</small>' +
                '</li>';
        });
        notificationList.innerHTML = notificationsHTML;
    }
}


function markAsRead(notificationId) {
    console.log("Marking notification as read:", notificationId);
    fetch(`${pageContext.request.contextPath}/notifications/read/` + notificationId, { method: 'POST' })
        .then(response => {
            // Check if the response is JSON
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.includes("application/json")) {
                return response.json();
            } else {
                return response.text(); // Handle non-JSON responses
            }
        })
        .then(data => {
            if (typeof data === "object") {
            } else {
            }

            const notification = notifications.find(n => n.notifId === notificationId);
            if (notification) {
                notification.read = true;
                updateNotificationCount();
                populateNotifications();
            }

            location.reload(); // Reload the page
        })
        .catch(error => {
            console.error("Error marking notification as read:", error);
        });
}




// Toggle notification modal visibility
function toggleNotifications() {
    const modal = document.getElementById('notificationModal');
    if (modal.style.display === 'none') {
        modal.style.display = 'block'; // Show the modal
        fetchNotifications(); 

        
      } else {
        modal.style.display = 'none'; // Hide the modal
      }    
  
}

// Fetch notifications on page load
document.addEventListener('DOMContentLoaded', () => {
    fetchNotifications(); 
});

	

	
	</script>