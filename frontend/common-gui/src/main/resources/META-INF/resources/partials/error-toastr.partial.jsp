<%@page import="de.creditreform.n5.core.translation.Translator"%>
<script type="text/ng-template" id="toasterBodyTmpl.html">
<div>
    <p>
        <b><%=Translator.translate("toastr.error.header", true) %>
        <span id="error.head.code" ng-show="toaster.body.code"> ({{toaster.body.code}})</span></b>
    </p>
     <p>
        <%=Translator.translate("toastr.error.call.support", true) %>
     </p>
     <table style="background-color:transparent; outline-style:none;">
         <thead>
             <tr>
                 <th colspan="2"><%=Translator.translate("toastr.error.provide.information", true) %></th>
             </tr>
         </thead>
        <tbody>
            <tr valign="top">
                <td align="right" style="padding: 0px 10px 0px 0px" width="10%"><b><%=Translator.translate("toastr.error.reason", true) %></b></td>
                <td id="error.reason" >{{toaster.body.reason || "N/A"}}</td>
           </tr>
           <tr valign="top">
                <td align="right" style="padding: 0px 10px 0px 0px" width="10%"><b><%=Translator.translate("toastr.error.source", true) %></b></td>
                <td id="error.source" >{{toaster.body.source || "N/A"}}</td>
            </tr>
            <tr valign="top">
                 <td align="right" style="padding: 0px 10px 0px 0px" width="10%"><b><%=Translator.translate("toastr.error.code", true) %></b></td>
                 <td id="error.code" >{{toaster.body.code || "N/A"}}</td>
            </tr>
            <tr valign="top">
                <td align="right" style="padding: 0px 10px 0px 0px" width="10%"><b><%=Translator.translate("toastr.error.uuid", true) %></b></td>
                <td id="error.uuid" >{{toaster.body.uuid || "N/A"}}</td>
            </tr>
        </tbody>
     </table>
</div>
</script>