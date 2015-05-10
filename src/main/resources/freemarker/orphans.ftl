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

    <div id="chart_div" class="sixteen columns">
        <div id="chart" style="height:1000px;"></div>
    </div>

<#assign elkeys = allInMap?keys>
<#list elkeys as elkey>
    <p>Allin: ${elkey}</p>

    <#assign newkeys = allInMap[elkey]?keys>
    <#assign newmap = allInMap[elkey]>
    <#list newkeys as newkey>
        <p>Key: ${newkey} : ${newmap[newkey].getOrphanedProperties()}</p>
    </#list>

</#list>


</div>
<#include "footer.ftl">
</body>
</html>


<script>
    $(function() {
        var chart = c3.generate({
            bindto: '#chart',
            /*
            size: {
                width: 5000,
                height: 1000
            },*/
            data: {
                x: 'x',
    //        xFormat: '%Y%m%d', // 'xFormat' can be used as custom format of 'x'
                columns: [
                    ['x', <#list allKnownDates as date>'${date}',</#list>],

                <#assign totes = accruedTotals?keys>
                <#list totes as k>
                    ['${k}', <#list accruedTotals[k] as item>${item}, </#list>],
                </#list>

                ]
            },
            axis: {
                x: {
                    type: 'timeseries',
                    tick: {
                        format: '%Y-%m-%d'
                    }
                }
            }
        });
    });
</script>