<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">

    <div class="sixteen columns">
        <h2>MarkLogic Fragment Counts <small>Date view</small></h2>
        <#include "navigation.ftl">
        <h3>Date: <small>${id}</small></h3>
    </div>

<#assign elkeys = pertainingToDate?keys>
<#list elkeys as elkey>
    <div class="well">
        <h4><span class="red">Host / Forest: </span>${elkey}</h4>
    </div>
    <dl class="dl-horizontal">
        <dt>Date</dt>
        <dd>${pertainingToDate[elkey].getDate()}</dd>
        <dt>Orphaned Properties Fragments</dt>
        <dd>${pertainingToDate[elkey].getOrphanedProperties()}</dd>
        <dt>Total Fragments Ingested per Forest</dt>
        <dd>${pertainingToDate[elkey].getTotalFragmentsIngestedByForest()}</dd>
        <dt>Total Fragments in Database</dt>
        <dd>${pertainingToDate[elkey].getTotalFragmentsIngestedInDatabase()}</dd>
        <dt>Active Fragments</dt>
        <dd>${pertainingToDate[elkey].getActiveFragments()}</dd>
        <dt>Nascent Fragments</dt>
        <dd>${pertainingToDate[elkey].getNascentFragments()}</dd>
        <dt>Deleted Fragments</dt>
        <dd>${pertainingToDate[elkey].getDeletedFragments()}</dd>
    </dl>
</#list>

</div>
<#include "footer.ftl">
</body>
</html>
