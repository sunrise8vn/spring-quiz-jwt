CREATE DEFINER = CURRENT_USER PROCEDURE `sp_insert_quiz_answer`(
    IN answers longtext,
    IN done TINYINT,
    IN quiz_id INT,
    IN quiz_question_id INT,
    IN student_id INT
)
BEGIN
    INSERT INTO quiz_answers (answers, done, quiz_id, quiz_question_id, student_id)
    VALUES (answers, done, quiz_id, quiz_question_id, student_id)

END;