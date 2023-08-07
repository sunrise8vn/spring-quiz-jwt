CREATE PROCEDURE `sp_get_questions_random`(
    IN numberQuestion INT
)
BEGIN
    SELECT
        quest.id,
        quest.content,
        quest.type
    FROM questions AS quest
    ORDER BY RAND()
    LIMIT numberQuestion;
END