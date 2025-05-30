/*IMPORTANT - All constants added here which will also be used in java side; need to be added in
 * /internsorbit/src/main/java/com/cluebix/internsorbit/utility/ProjectConstants.java file too*/
/*notification constants*/
const notif_offset=15;
const notifications_status_unread='U';
const notifications_status_read='R';

/*feedback constants*/
const feed_param_very_good=5;
const feed_param_good=4;
const feed_param_fair=3;
const feed_param_poor=2;
const feed_param_very_poor=1;
/*students education initials*/
const student_education_ug="UG";
const student_education_hsc="HSC";
const student_education_ssc="SSC";
const date_format="yyyy-MM-dd";
const user_initials_college="CL";
const user_initials_company="CP";
const user_initials_student="ST";
const user_initials_users="US";
const hr_Role=1;
const manager_Role=2;
const internship_lead_Role=3;
const iii_Role=1;
const hod_Role=2;
const tpheadiiicell="6"
const dept_incharge_Role=3;
const limit=7;
const request_student_limit=6;
const internships_status_pending="P";
const internships_status_ongoing="O";
const internships_status_completed="C";
const internships_status_cancelled="X";
const coll_comp_tieup_status_pending="P";
const coll_comp_tieup_status_approved="A";
const coll_comp_tieup_status_cancelled="C";
const coll_comp_tieup_status_rejected="R";
const coll_dept_link_status_active="A";
const coll_dept_link_status_deactivate="D";
const coll_dept_link_status_deleted="C";
const coll_internal_req_approval_status_HOD=""+hod_Role;
const coll_internal_req_approval_status_III=""+tpheadiiicell;
const coll_internal_req_approval_status_approved="A";
const coll_internal_req_approval_status_rejected="R";
const coll_internal_req_approval_status_cancelled="C";
const comp_advertisements_status_active="A";
const comp_advertisements_status_deleted="D";
const comp_dept_link_status_active="A";
const comp_dept_link_status_deactivate="D";
const comp_dept_link_status_deleted="C";
const comp_internal_req_approval_status_manager=""+manager_Role;
/*const comp_internal_req_approval_status_internship_lead=""+internship_lead_Role;*/
const comp_internal_req_approval_status_approved="A";
const comp_internal_req_approval_status_cancelled="C";
const external_req_approval_status_pending="P";
const external_req_approval_status_approved="A";
const external_req_approval_status_rejected="R";
const external_req_approval_status_cancelled="C";
const external_req_approval_status_assigned="S";
const external_req_approval_status_test_assigned="T";
const reg_user_temp_pass_yes="Y";
const reg_user_temp_pass_no="N";
const student_req_approval_status_pending="P";
const student_req_approval_status_approved="A";
const student_req_approval_status_rejected="R";
const student_req_approval_status_cancelled="C";
const student_req_approval_status_test_assigned="T";
const student_req_approval_status_result_declared="D";
const func_status_clg_default_pkg="CLG_DEFAULT_PKG";
const func_status_com_default_pkg="COM_DEFAULT_PKG";
const func_status_func_value_on="Y";
const package_data_package_type_default="D";
const package_data_package_type_recharge="R";
const package_data_package_type_topup="T";
const package_data_package_for_college="COL";
const package_data_package_for_company="COM";
const package_data_package_for_student="STU";
const weekly_schedule_internship="W";
const ajax_return_success = "success";
const ajax_return_failure = "failure";
const raise_req_student_limit_rchd="Sorry! Only "+request_student_limit+" students can be selected together for a internship request.";
const raise_req_student_req_pndng="There is already a request pending for this student at this company.";
const raise_req_conf_msg_tech="Are you sure, you want to raise the request without selecting a Skill?";
const raise_req_conf_msg_dur="Are you sure, you want to raise the request without selecting a Duration?";
const raise_req_conf_msg_tech_dur="Are you sure, you want to raise the request without selecting Skill & Duration?";
const interncoins_exhausted_temp="Oops! Your InternCoins have exhausted. Please contact the the admin at ++91- 9422926439 for further procedure";
const interncoins_exhausted="Oops! Your InternCoins have exhausted. Would you like to add more coins to the current balance?";
const interncoins_exhausted_college="Oops! The respective college's InternCoins have exhausted. A notification has been sent to the college. Please try again after sometime.";
const top_up_success="Congrats! Requested number of InternCoins have been added to your account.";
const select_a_package="Please select one of the available packages to proceed.";
const select_a_balance="Please select one of the available balances to proceed.";
const contact_io_team="Please contact The SkillPilots Team at ++91- 9422926439.";
const create_plan_internship="The internship(s) has been scheduled. Would you like to lay down a plan for the internship(s) for the interns to get prepared.";
const edit_plan_internship="The internship(s) has been scheduled. Would you like to edit the plan for the internship(s).";
const no_plan_internship="The schedule for this internship advertisement hasn\'t been posted yet.";
const email_exist="Entered Email-id is already registered with us.";
const company_exist="Entered Company is already registered with us.";
const already_has_ongoing_internship="There is already an ongoing internship of this student.";
const already_have_ongoing_internship="You already have an internship in process.";
const test_disqualified="D";
const online_test_func_name="online_test_functionality";
const online_test_func_value_No="N";
const online_test_type_for_test="T";
const online_test_type_for_section="S";
const online_test_type_for_question="Q";
const company_test_result_passed="Passed";
const company_test_result_failed="Failed";
const request_cancelled_by_system="X";
const total_no_of_ques_per_test=75;
const company_test_status_active="A";
const company_test_status_inactive="I";
const student_test_data_id_initial="TD";
const student_test_data_status_pending="P";
const student_test_data_status_passed="C";
const student_test_data_status_failed="F";
const student_test_data_status_disqualified="D";
const student_test_validity_days=5;
const student_request_id_initial="SR";
const external_request_id_initial="ER";
const button_yes = 'Yes';
const button_no = 'No';
const tutorial_video_link_coll_role_1="${pageContext.request.contextPath}/IO_tutorial_institute_role_1.mp4";
const tutorial_video_link_coll_role_2="${pageContext.request.contextPath}/IO_tutorial_institute_role_2.mp4";
const tutorial_video_link_coll_role_3="${pageContext.request.contextPath}/IO_tutorial_institute_role_3.mp4";
const tutorial_video_link_comp_role_1="${pageContext.request.contextPath}/IO_tutorial_industry_role_1.mp4";
const tutorial_video_link_comp_role_2="${pageContext.request.contextPath}/IO_tutorial_industry_role_2.mp4";
const tutorial_video_link_comp_role_3="${pageContext.request.contextPath}/IO_tutorial_industry_role_3.mp4";
const tutorial_video_link_student="${pageContext.request.contextPath}/IO_tutorial_student.mp4";

//Error Messages
// const error_fetch="Oops! Some error occured while fetching data. Please try again after sometime or contact the SkillPilots Team.";
// const error_save="Oops! Some error occured while saving data. Please try again after sometime or contact the SkillPilots Team.";
// const error_verify="Oops! Some error occured while verifying data. Please try again after sometime or contact the SkillPilots Team.";
// const error_fetch="Oops! Some error occured while fetching data. Please try again after sometime or contact the SkillPilots Team."