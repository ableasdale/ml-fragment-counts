<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">

    <div class="sixteen columns">
        <h2>MarkLogic Fragment Counts <small>Orphaned Fragments</small></h2>
        <#include "navigation.ftl">
    </div>

    <div id="chart_div" class="sixteen columns">
        <div id="chart"></div>
    </div>

    <div class="row even-spaced">
        <div class="col-md-3"><a href="#" class="btn btn-primary savePNG disabled"><span class="glyphicon glyphicon-download" aria-hidden="true"></span> Download chart as PNG image</a></div>
        <div class="col-md-3"><a href="#" class="btn btn-primary disabled"><span class="glyphicon glyphicon-download" aria-hidden="true"></span> TODO: Download chart as SVG image</a></div>
    </div>

    <hr class="bottom-spaced"/>

<!--
<#assign elkeys = allInMap?keys>
<#list elkeys as elkey>
    <p>Allin: ${elkey}</p>

    <#assign newkeys = allInMap[elkey]?keys>
    <#assign newmap = allInMap[elkey]>
    <#list newkeys as newkey>
        <p>Key: ${newkey} : ${newmap[newkey].getOrphanedProperties()}</p>
    </#list>

</#list>
    -->

</div>
<#include "footer.ftl">
</body>
</html>


<script>
    $(function() {
        var chart = c3.generate({
            bindto: '#chart',
            size: {
                width: 12000,
                height: 800
            },
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