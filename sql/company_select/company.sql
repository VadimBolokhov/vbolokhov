/*
 Retrieve in a single query:
 - names of all persons that are NOT in the company with id = 5
 - company name for each person
*/
SELECT p.name, c.name
    FROM person AS p, company AS c
    WHERE p.company_id != 5 AND c.id = p.company_id;

/*
 Select the name of the company with the maximum 
 number of persons + number of persons in this company
*/
SELECT c.name, COUNT(*) AS c_count
    FROM person AS p
        INNER JOIN company AS c
            ON c.id = company_id
    GROUP BY c.name
    ORDER BY c_count DESC
    LIMIT 1

