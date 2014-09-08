<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.j7.system.core.translation.Translator"%>
<html>
    <head>
    </head>
    <body>

        <div class="modal-header" style="padding-top:5px;padding-bottom:5px;">
            <div><h4><span class="glyphicon glyphicon-file"></span> <%=Translator.translate("label.inquiry.text")%></h4></div>
        </div>
        <div id="inquiryPrintArea" class="modal-body" style="padding:7px;">
            <pre class="inquiryText">{{data}}</pre>
        </div>
        <div class="modal-footer">

            <a ng-href="{{pdfURL}}" type="button" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-save"></span> <%=Translator.translate("button.save.pdf")%>
            </a>

            <print-btn id="print" print-div-id="inquiryPrintArea"><%=Translator.translate("button.print")%></print-btn>

            <button id="close" class="btn btn-primary btn-sm" ng-click="close()">
                <span class="glyphicon glyphicon-remove"></span> <%=Translator.translate("button.close")%></button>
        </div>
    </body>
</html>