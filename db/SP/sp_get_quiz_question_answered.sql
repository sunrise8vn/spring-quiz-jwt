CREATE PROCEDURE `sp_get_quiz_question_answered`(
    IN quizId INT
)
BEGIN
    SELECT
        qq.id,
        CASE
            WHEN qq.id = qa.quiz_question_id
            THEN 'TRUE' ELSE 'FALSE'
        END AS 'checked'
    FROM `quiz_questions` AS qq
    LEFT JOIN quiz_answers AS qa
    ON qq.id = qa.quiz_question_id
    WHERE qq.quiz_id = quizId;
END