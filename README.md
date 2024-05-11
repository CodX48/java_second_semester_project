<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Student Management System</title>
</head>
<body>
  <h1>Student Management System</h1>

<h2>Overview</h2>
  <p>This project is a simple student management system implemented in Java. It allows users to perform various operations related to student management, such as adding students, enrolling them in courses, searching for students, deleting students, updating student information, and sending student information via email or writing to a file.</p>

<h2>Features</h2>
  <ul>
    <li><strong>Add Student:</strong> Add new students to the system, with the option to enroll them in courses.</li>
    <li><strong>Enroll in Courses:</strong> Enroll students in courses from a predefined list of available courses.</li>
    <li><strong>Search Students:</strong> Search for students by ID or name.</li>
    <li><strong>Delete Student:</strong> Delete students from the system.</li>
    <li><strong>Update Student Information:</strong> Update student information, including enrolled courses.</li>
    <li><strong>Data Transfer:</strong>
      <ul>
        <li>Send student information via email.</li>
        <li>Write student information to a file.</li>
      </ul>
    </li>
  </ul>

<h2>File Structure</h2>
  <ul>
    <li><code>Main.java</code>: Contains the main class and the main menu for the user interface.</li>
    <li><code>Student.java</code>: Represents the Student entity and provides methods for student management operations.</li>
    <li><code>Course.java</code>: Represents the Course entity and provides methods for course-related operations.</li>
    <li><code>Sys/DataTrans/DataTransfer.java</code>: Abstract class for data transfer operations.</li>
    <li><code>Sys/DataTrans/Email.java</code>: Sends student information via email.</li>
    <li><code>Sys/DataTrans/ToFile.java</code>: Writes student information to a file.</li>
  </ul>

<h2>Usage</h2>
  <ol>
    <li>Clone the repository to your local machine.</li>
    <li>Open the project in your preferred Java IDE.</li>
    <li>Compile and run the <code>Main.java</code> file.</li>
    <li>Follow the on-screen instructions to perform various student management operations.</li>
  </ol>

<h2>Dependencies</h2>
  <ul>
    <li>JavaMail API (for sending emails)</li>
  </ul>

<h2>Contributors</h2>
  <ul>
    <li>MOSTAFA IBRAHIM</li>
  </ul>

<h2>License</h2>
  <p>This project is licensed under the <a href="LICENSE">MIT License</a>.</p>
</body>
</html>
