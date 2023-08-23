CREATE PROCEDURE `sp_get_questions_random`(
    IN categoryId INT,
    IN numberQuestion INT
)
BEGIN
    SELECT
        quest.id,
        quest.content,
        quest.type
    FROM questions AS quest
    WHERE quest.category_id = categoryId
    ORDER BY RAND()
    LIMIT numberQuestion;
END