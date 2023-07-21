<?php

// Creating the Connection
$conn = mysqli_connect("localhost", "root", "", "quiz_db");

// Check if the connection was successful
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Correct SQL query with backticks and parameter binding
$query = "SELECT question, option1, option2, option3, option4, correct_option FROM math_quiz";

// Executing the Query
$stmt = $conn->prepare($query);
$stmt->execute();

// Check for query execution errors
if ($stmt === false) {
    die("Error executing the query: " . mysqli_error($conn));
}

// Binding the results to variables
$stmt->bind_result($question, $option1, $option2, $option3, $option4, $correct_option);

// Creating an empty array
$questions_array = array();

// Traversing through all the Questions
while ($stmt->fetch()) {
    $temp = array();
    $temp['question'] = $question;
    $temp['option1'] = $option1;
    $temp['option2'] = $option2;
    $temp['option3'] = $option3;
    $temp['option4'] = $option4;
    $temp['correct_option'] = $correct_option;

    array_push($questions_array, $temp);
}

// Display the results in JSON Format
echo json_encode($questions_array);

// Close the database connection
$stmt->close();
$conn->close();

?>
