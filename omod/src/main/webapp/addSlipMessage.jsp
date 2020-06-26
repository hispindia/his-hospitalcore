<%--
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Registration module.
 *
 *  Registration module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Registration module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Registration module.  If not, see <http://www.gnu.org/licenses/>.
 *
--%>
<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="./includes/js_css.jsp" %>
<openmrs:require privilege="Add Patients" otherwise="/login.htm" redirect="/module/hospitalcore/addSlipMessage.form" />

<script type="text/javascript">

    jQuery(document).ready(function () {
        jQuery("#btnSave").click(function (e) {
            e.preventDefault();
            var slipMessage = jQuery("#slipMessage").val();
            console.log(slipMessage);

            jQuery.post(window.location.href, { "message": "" + slipMessage }, function (data, status) {
                if (status == "success" && data == "success") {
                    alert("Success! Message updated successfully!");
                    window.location.reload();
                } else {
                    alert("Error! while updating the message. Please try again.");
                    window.location.reload();
                }
            });
        });

    })


</script>
<h1>Add Message</h1>
<textarea name="slipMessage" id="slipMessage" cols="100" rows="10"
    style="padding: 5px;"
    placeholder="Type your message here...">${message}</textarea>
<br><br>
<button id="btnSave" style="padding: 5px;">Save</button>