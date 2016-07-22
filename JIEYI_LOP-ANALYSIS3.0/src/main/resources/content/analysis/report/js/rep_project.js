function goLink(){
        top.addTab("数据明细",_basePath+"/fusionChart/Report!goLink.action");
    }
    
    function goVersion(val){
        frm.action = "Report!goReportView.action?velocity=analysis/report/rep_project.vm&CARD_CODE=项目收益&MODULE_CODE=项目报表模块&GOID=" + val;
        frm.submit();
    }
    
    $(document).ready(function(){
    
        qd();
        
        $("#RENTER_NAME").keydown(function(e){
            if (e.keyCode == 13) {
                cx();
            }
        });
        
    })
    
    
    function qd(){
        var LESSEE_CODE = $("#RENTER_CODE").val();
        $("#flash").empty();
        $("#flash").append("<embed type='application/x-shockwave-flash' src='../analysis/biReport/charts/MSLine.swf' id='ChartId' name='ChartId' quality='high' wmode='transparent' allowscriptaccess='always' flashvars='chartWidth=99%&chartHeight=99%&debugMode=0&DOMId=ChartId&registerWithJS=0&scaleMode=noScale&lang=EN&baseFontSize=10&dataURL=$request.contextPath/fusionChart/DataRelease!doSelectDataXML.action?ID=$!ID%26CHART_ID=4%26LESSEE_CODE=" + LESSEE_CODE + "' height='350px' width='49%'>")
    }
    
    function cx(){
        var RENTER_NAME = $("#RENTER_NAME").val();
        var GOID = $("#REPORT_NAME").val();
        jQuery.ajax({
            url: "Report!selectLessee.action?RENTER_NAME=" + RENTER_NAME + "&CARD_CODE=项目收益" + "&MODULE_CODE=项目报表模块" + "&GOID=" + GOID,
            dataType: "json",
            success: function(json){
                $("#RENTER_CODE").empty();
                for (var i = 0; i < json.length; i++) {
                    $("#RENTER_CODE").append("<option value='" + json[i].RENTER_CODE + "' >" + json[i].RENTER_NAME + " / " + json[i].RENTER_CODE + "</option>");
                }
                qd();
            }
        });
    }