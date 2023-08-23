CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_quiz_question_by_quiz_id`(
    IN quizId INT,
    IN offsetIndex INT
)
BEGIN
    SELECT
        qu.quiz_exam_id AS quizExamId,
        qq.id AS quizQuestionId,
        qq.question_id AS questionId,
        qe.number_question AS numberQuestion,
        qu.created_at AS startedOn,
        qe.minutes AS minutes,
        qq.question_content AS questionContent,
        qq.type AS questionType
    FROM quiz_questions AS qq
    JOIN quiz AS qu
    ON qq.quiz_id = qu.id
    JOIN quiz_exams AS qe
    ON qe.id = qu.quiz_exam_id
    WHERE qq.quiz_id = quizId
    ORDER BY qq.id
    LIMIT 1
    OFFSET offsetIndex
    ;

END