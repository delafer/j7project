<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.j7.system.core.translation.Translator"%>
<div class="row">
    <div class="col-sm-12 ">
        <form class="form-horizontal" role="form" name="searchForm">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <span class="glyphicon glyphicon-info-sign"></span> <span>
                            <%=Translator.translate("label.metadata")%></span>
                    </h3>
                </div>

                <div class="panel-body">
                    <fieldset disabled name="searchFields">

                        <div class="form-group">
                            <label for="crefoId" class="col-sm-2 control-label"><%=Translator.translate("label.crefoId.version")%></label>

                            <div class="col-sm-3">
                                <input type="text" class="form-control input-sm input-sm"
                                       name="crefo" autofocus ng-model="data.crefoNr">
                            </div>
                            <div class="col-sm-1">
                                <input type="number" class="form-control input-sm input-sm"
                                       name="vrsn" ng-model="data.csvmetaData.version">
                            </div>

                            <label for="country" class="col-sm-2 control-label"><%=Translator.translate("label.country")%></label>
                            <div class="col-sm-4">


                                <input type="text" class="form-control input-sm" name="country"
                                       ng-model="data.csvmetaData.country">

                            </div>
                        </div>
                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label"><%=Translator.translate("label.name")%></label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control input-sm" name="name"
                                       value="{{data.csvmetaData.name1}} {{data.csvmetaData.name2}} {{data.csvmetaData.name3}}">
                            </div>

                            <label for="legalForm" class="col-sm-2 control-label"><%=Translator.translate("label.legalform")%></label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control input-sm"
                                       name="legalForm" ng-model="data.csvmetaData.legalForm">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="street" class="col-sm-2 control-label"><%=Translator.translate("label.street.no")%></label>
                            <div class="col-sm-2">
                                <input type="text" class="form-control input-sm" name="street"
                                       ng-model="data.csvmetaData.street">
                            </div>
                            <div class="col-sm-1"
                                 ng-class="{'has-error': searchForm.homeNr.$invalid}">
                                <input type="number" class="form-control input-sm" name="homeNo"
                                       ng-model="data.csvmetaData.homeNr">
                            </div>
                            <div class="col-sm-1">
                                <input type="text" class="form-control input-sm"
                                       name="homeSuffix" ng-model="data.csvmetaData.addressExt">
                            </div>
                            <label for="zip" class="col-sm-2 control-label"><%=Translator.translate("label.zip.city")%></label>
                            <div class="col-sm-1">
                                <input type="string" class="form-control input-sm" name="zip"
                                       ng-model="data.csvmetaData.zip">
                            </div>
                            <div class="col-sm-3">
                                <input type="text" class="form-control input-sm" name="city"
                                       ng-model="data.csvmetaData.city">
                            </div>
                        </div>


                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label"><%=Translator.translate("label.boniindex")%></label>
                            <div class="col-sm-1">
                                <input type="text" class="form-control input-sm"
                                       style="font-weight: bold" name="boni"
                                       ng-model="data.csvmetaData.boniIndex">
                            </div>
                        </div>

                    </fieldset>

                </div>
                <!-- panel body-->
                <div class="panel-footer">


                    <button type="button" id="inquiry" class="btn btn-default btn-sm"
                            ng-click="viewInquiry()">
                        <span class="glyphicon" ng-class="(showInquiry==false) ? 'glyphicon-eye-open' : 'glyphicon-eye-close'"></span> {{(showInquiry==false) ? '<%=Translator.translate("show.inquiry.text")%>' : '<%=Translator.translate("hide.inquiry.text")%>'}}
                    </button>
                    <a ng-href="{{pdfURL}}" type="button" class="btn btn-default btn-sm">
                        <span class="glyphicon glyphicon-save"></span> <%=Translator.translate("button.save.pdf")%>
                    </a>
                    <print-btn disable-btn="!showInquiry" print-div-id="inquiryPrintArea"><%=Translator.translate("button.print")%></print-btn>
                    <a ng-href="#!/" type="submit" class="btn btn-primary btn-sm pull-right"><%=Translator.translate("button.back")%></a>
                </div>
            </div>


        </form>
    </div>
</div>
<div class="row" ng-show="showInquiry">
    <div class="col-sm-12 ">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">
                    <span class="glyphicon glyphicon-file"></span> <span>
                        <%=Translator.translate("label.inquiry.text")%></span>
                </h3>
            </div>

            <div class="panel-body">

                <div id="inquiryPrintArea" class="modal-body"
                     style="padding: 7px;">
                        <pre class="inquiryText">{{inquiryText}}</pre>
                </div>
            </div>
        </div>
    </div>
</div>
</div>






