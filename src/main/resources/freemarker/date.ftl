<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">

    <div class="sixteen columns">
        <h2>MarkLogic Fragment Counts <small>Dashboard</small></h2>
        <#include "navigation.ftl">
        <h3>Current file: <small>TODO</small></h3>
    </div>

<#assign elkeys = pertainingToDate?keys>
<#list elkeys as elkey>

     <h3>${elkey}</h3>


        <p>Date ${pertainingToDate[elkey].getDate()}</p>
        <p>Orphaned Props ${pertainingToDate[elkey].getOrphanedProperties()}</p>
        <p>Frags Ingested by forest ${pertainingToDate[elkey].getTotalFragmentsIngestedByForest()}</p>
        <p>Total Frags ${pertainingToDate[elkey].getTotalFragmentsIngestedInDatabase()}</p>

        <p>Active Frags ${pertainingToDate[elkey].getActiveFragments()}</p>
        <p>Nascent Frags ${pertainingToDate[elkey].getNascentFragments()}</p>
        <p>Deleted Frags ${pertainingToDate[elkey].getDeletedFragments()}</p>


</#list>

</div>
<#include "footer.ftl">
</body>
</html>
