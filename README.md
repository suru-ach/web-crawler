## Multi-threaded Web Crawler

The program performs two primary operations
- Task management, It to manage a set of tasks that run the web crawlers.
- HTML Page Scraping, Each instance of the web crawler retrieves an HTML page and extracts the links from it.

There are two approaches that could be implemented. Since the follwoing only scrapes for links.
- One approach is to use a list of links by adding a query parameter to the base link (example http://test.com/1, here the query could be from 1 to 10)
- Another approach is to save a link that would call many other links and further on (with a limit on the number of nesting possible). The follownig program has taken the former approach.

Take a look at the TODO for the implementation details.
