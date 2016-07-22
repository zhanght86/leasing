    var ID = document.getElementById('ID').value;
	var url = _basePath+"/pdfTemplate/PdfTemplateManagement!downPdfTemplate.action?ID="+encodeURI(ID);

    PDFJS.disableWorker = true;

    var pdfDoc = null,
        pageNum = 1,
        scale = 0.75,
        canvas = document.getElementById('the-canvas'),
        ctx = canvas.getContext('2d');

    function renderPage(num) {
      pdfDoc.getPage(num).then(function(page) {
        var viewport = page.getViewport(scale);
        canvas.height = viewport.height;
        canvas.width = viewport.width;

        var renderContext = {
          canvasContext: ctx,
          viewport: viewport
        };
        page.render(renderContext);
      });

      document.getElementById('page_num').textContent = pageNum;
      document.getElementById('page_count').textContent = pdfDoc.numPages;
    }

	 function renderPage1(num,scale) {
      pdfDoc.getPage(num).then(function(page) {
        var viewport = page.getViewport(scale);
        canvas.height = viewport.height;
        canvas.width = viewport.width;

        var renderContext = {
          canvasContext: ctx,
          viewport: viewport
        };
        page.render(renderContext);
      });

      document.getElementById('page_num').textContent = pageNum;
      document.getElementById('page_count').textContent = pdfDoc.numPages;
    }

	function proportion() {
	  scale = document.getElementById('scaleSelect').value;
      renderPage1(pageNum,scale);
    }

	function tiaozhuan(){
		scale = document.getElementById('scaleSelect').value;
		pageNum = document.getElementById('tiaozhuanshu').value;
		renderPage1(pageNum,scale);
	}

    function goPrevious() {
      if (pageNum <= 1)
        return;
      pageNum--;
      renderPage(pageNum);
    }

    function goNext() {
      if (pageNum >= pdfDoc.numPages)
        return;
      pageNum++;
      renderPage(pageNum);
    }

    PDFJS.getDocument(url).then(function getPdfHelloWorld(_pdfDoc) {
      pdfDoc = _pdfDoc;
      renderPage(pageNum);
    });
