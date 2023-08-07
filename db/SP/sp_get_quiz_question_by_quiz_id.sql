CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_quiz_question_by_quiz_id`(
    IN quizId INT,
    IN offsetIndex INT
)
BEGIN
    SELECT
        (
            SELECT qu.quiz_exam_id
            FROM quiz AS qu
            WHERE qu.id = quizId
        ) AS quizExamId,
        qq.id AS quizQuestionId,
        qq.question_id AS questionId,
        (
            SELECT number_question
            FROM quiz_exams AS qe
            WHERE qe.id = (
                SELECT qu.quiz_exam_id
                FROM quiz AS qu
                WHERE qu.id = quizId
            )
        ) AS numberQuestion,
        qq.question_content AS questionContent,
        qq.type AS questionType
    FROM quiz_questions AS qq
    WHERE qq.quiz_id = quizId
    ORDER BY id
    LIMIT 1
    OFFSET offsetIndex
    ;

END