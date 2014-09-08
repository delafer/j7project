<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.j7.system.core.translation.Translator"%>

<div class="row">
    <div class="col-sm-12 ">
        <form class="form-horizontal" role="form" name="searchForm" ng-submit="searchForm.$valid && search()">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <span class="glyphicon glyphicon-edit"></span> <span> <%=Translator.translate("search.fields")%></span>
                    </h3>
                </div>

                <div class="panel-body">
                    <fieldset name="searchFields">

                        <div ng-show="searchForm.$invalid" class="alert alert-warning">

                            <p>
                                <span ng-show="searchForm.vrsn.$error.number"><%=Translator.translate("wrong.version.no")%></span>
                            </p>
                            <p>
                                <span ng-show="searchForm.homeNo.$error.number"><%=Translator.translate("wrong.home.no")%></span>
                            </p>
                        </div>
                        <div class="form-group">
                            <label for="crefoId" class="col-sm-2 control-label"><%=Translator.translate("label.crefoId.version")%></label>

                            <div class="col-sm-3">
                                <input type="text" class="form-control input-sm input-sm" name="crefo" autofocus placeholder="0123456789" ng-model="searchParams.crefoNr">
                            </div>
                            <div class="col-sm-1">
                                <input type="number" min="0" class="form-control input-sm input-sm" name="vrsn" ng-model="searchParams.version" placeholder="1">
                            </div>

                            <label for="country" class="col-sm-2 control-label"><%=Translator.translate("label.country")%></label>
                            <div class="col-sm-4" ng-controller="CountrySelectCtrl">
                                
                                <div class="input-group">
                                    <ui-select id="countrySelect" ng-model="comboParams.countryCode" theme="bootstrap">
                                        <ui-select-match placeholder="<%=Translator.translate("label.select.country")%>">{{comboParams.countryCode.name}} <small>({{comboParams.countryCode.key}})</small></ui-select-match>
                                        <ui-select-choices repeat="country in countries | filter: $select.search">
                                            <span ng-bind-html="country.name | highlight: $select.search"></span>
                                            <small>(</small><small ng-bind-html="country.key | highlight: $select.search"></small><small>)</small>
                                        </ui-select-choices>
                                    </ui-select>

                                    <span class="input-group-btn ">
                                        <a ng-click="comboParams.countryCode = undefined" class="btn btn-mini btn-default">
                                            <span class="glyphicon glyphicon-trash"></span>
                                        </a>
                                    </span>
                                </div>
                            </div>
                        </div>
                                        
                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label"><%=Translator.translate("label.name")%></label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control input-sm" name="name" ng-model="searchParams.name" placeholder="<%=Translator.translate("label.company.name")%>">
                            </div>

                            <label for="legalForm" class="col-sm-2 control-label"><%=Translator.translate("label.legalform")%></label>
                            <div class="col-sm-4" ng-controller="LegalFormSelectCtrl">
                                
                                <div class="input-group">

                                    <ui-select id="legalFormSelect" ng-model="comboParams.legalformCode" theme="bootstrap">
                                        <ui-select-match placeholder="<%=Translator.translate("label.select.legalform")%>">{{comboParams.legalformCode.name}}</ui-select-match>
                                        <ui-select-choices repeat="legalForm in legalForms | filter: $select.search">
                                            <span ng-bind-html="legalForm.name | highlight: $select.search"></span>
                                        </ui-select-choices>
                                    </ui-select>

                                    <span class="input-group-btn ">
                                        <a ng-click="comboParams.legalformCode = undefined" class="btn btn-mini btn-default">
                                            <span class="glyphicon glyphicon-trash"></span>
                                        </a>
                                    </span>

                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="street" class="col-sm-2 control-label"><%=Translator.translate("label.street.no")%></label>
                            <div class="col-sm-2">
                                <input type="text" class="form-control input-sm" name="street" ng-model="searchParams.street" placeholder="<%=Translator.translate("label.street")%>">
                            </div>
                            <div class="col-sm-1" ng-class="{'has-error': searchForm.homeNr.$invalid}">
                                <input type="number" min="1" class="form-control input-sm" name="homeNo" ng-model="searchParams.homeNr" placeholder="1">
                            </div>
                            <div class="col-sm-1">
                                <input type="text" class="form-control input-sm" name="homeSuffix" ng-model="searchParams.addressExt" placeholder="a/1">
                            </div>
                            <label for="zip" class="col-sm-2 control-label"><%=Translator.translate("label.zip.city")%></label>
                            <div class="col-sm-1">
                                <input type="string" class="form-control input-sm" name="zip" ng-model="searchParams.zip" placeholder="<%=Translator.translate("label.zip")%>">
                            </div>
                            <div class="col-sm-3">
                                <input type="text" class="form-control input-sm" name="city" ng-model="searchParams.city" placeholder="<%=Translator.translate("label.city")%>">
                            </div>
                        </div>

                    </fieldset>

                </div>
                <!-- panel body-->
                <div class="panel-footer">

                    <button type="submit" id="search" class="btn btn-success btn-sm"><%=Translator.translate("button.search")%></button>
                    <button type="reset" id="reset" ng-click="resetSearch()" class="btn btn-warning btn-sm"><%=Translator.translate("button.reset")%></button>

                </div>
            </div>
            <!-- panel -->
            <div class="well" ng-show="showSearchParams">
                <legend><%=Translator.translate("label.search.params")%></legend>
                <div>{{searchParams| json}}</div>
                <div>{{comboParams | json}}</div>
            </div>

        </form>
    </div>
</div>
<div class="row" ng-show="!dataList.length && searched">
    <div class="col-sm-12 ">
        <div class="panel panel-warning">
            <div class="panel-heading"><%=Translator.translate("header.no.data")%></div>
            <div class="panel-body"><%=Translator.translate("text.no.data")%></div>
        </div>
    </div>
</div>
<div class="row" ng-hide="!dataList.length">
    <div class="col-sm-12 ">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">
                    <span class="glyphicon glyphicon-list"></span>
                    <span ng-show="hitListOversize == null"><%=Translator.translate("label.user.hits")%></span>
                    <span ng-show="hitListOversize != null"><%=Translator.translate("label.hitlist")%> (<span ng-show="hitListOversize"><%=Translator.translate("label.more.than")%> {{maxHits}}</span><span ng-hide="hitListOversize">{{dataList.length}}</span> <%=Translator.translate("label.hit")%>)</span>
                </h3>
            </div>

            <div class="panel-body">
                <table ng-table="tableParams" class="table table-striped table-hover table-curved table-condensed" name="hitList">

                    <tr ng-repeat="elem in $data">
                        <td data-title="'<%=Translator.translate("column.actions")%>'"><input type="image" ng-click="showDetails(elem)" title="<%=Translator.translate("show.details")%>" src="images/icons/page_go.png" /> <input type="image"
                                                                                                                                                                                                        ng-click="open(elem.uuid)" title="<%=Translator.translate("show.inquiry.text")%>" src="images/icons/text_align_justify.png" /></td>
                        <td data-title="'<%=Translator.translate("column.crefo")%>'" sortable="'csvmetaData.crefoNr'">{{elem.csvmetaData.crefoNr}}</td>
                        <td data-title="'<%=Translator.translate("column.fullname")%>'" sortable="'csvmetaData.name1'">{{elem.csvmetaData.name1}}</td>
                        <td data-title="'<%=Translator.translate("column.legalform")%>'" sortable="'csvmetaData.legalForm'">{{elem.csvmetaData.legalForm}}</td>
                        <td data-title="'<%=Translator.translate("column.street")%>'" sortable="'csvmetaData.street'">{{elem.csvmetaData.street}} {{elem.csvmetaData.homeNr}} {{elem.csvmetaData.addressExt}}</td>
                        <td data-title="'<%=Translator.translate("column.country")%>'" sortable="'csvmetaData.country'">{{elem.csvmetaData.country}}</td>
                        <td data-title="'<%=Translator.translate("column.zip.city")%>'" sortable="'csvmetaData.zip'">{{elem.csvmetaData.zip}} {{elem.csvmetaData.city}}</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
