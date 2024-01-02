class PageModel{
    constructor(content, empty, first, last, numberOfElements, size, totalElements, totalPages) {
        this.content = content;
        this.empty = empty;
        this.first = first;
        this.last = last;
        this.numberOfElements = numberOfElements;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}

export default PageModel;