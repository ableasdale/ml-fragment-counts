<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">

    <div class="sixteen columns">
        <h2>MarkLogic Fragment Counts <small>Host view</small></h2>
    <#include "navigation.ftl">
        <h3>Host: <small>${currentHost}</small></h3>
    </div>

    <div class="sixteen columns">
        <div id="chart3"></div>
    </div>

    <div class="sixteen columns">
        <div id="chart2"></div>
    </div>

    <div class="row even-spaced">
        <div class="col-md-3"><a href="#" class="btn btn-primary savePNG disabled"><span class="glyphicon glyphicon-download" aria-hidden="true"></span> Download chart as PNG image</a></div>
        <div class="col-md-3"><a href="#" class="btn btn-primary disabled"><span class="glyphicon glyphicon-download" aria-hidden="true"></span> TODO: Download chart as SVG image</a></div>
    </div>

    <hr class="bottom-spaced"/>

</div>
<#include "footer.ftl">
</body>
</html>


<script>
    $(function() {
        var chart = c3.generate({
            bindto: '#chart3',
            size: {
                width: 3000,
                height: 500
            },
            data: {
                type: 'spline',
                x: 'x',
                //        xFormat: '%Y%m%d', // 'xFormat' can be used as custom format of 'x'
                columns: [
                    ['x', <#list forestData as item>'${item.getDate()}',</#list>],
                    ['Nascent Fragments', <#list forestData as item>${item.getNascentFragments()}, </#list>],
                    ['Orphaned Fragments', <#list forestData as item>${item.getOrphanedProperties()}, </#list>]

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

        var chart2 = c3.generate({
            bindto: '#chart2',
            size: {
                width: 3000,
                height: 800
            },
            data: {
                type: 'spline',
                x: 'x',
                //        xFormat: '%Y%m%d', // 'xFormat' can be used as custom format of 'x'
                columns: [
                    ['x', <#list forestData as item>'${item.getDate()}',</#list>],
                    ['Active Fragments', <#list forestData as item>${item.getActiveFragments()}, </#list>],
                    ['Deleted Fragments', <#list forestData as item>${item.getDeletedFragments()}, </#list>],
                    ['Total Fragments / Forest', <#list forestData as item>${item.getTotalFragmentsIngestedByForest()}, </#list>],
                    ['Total Fragments / DB', <#list forestData as item>${item.getTotalFragmentsIngestedInDatabase()}, </#list>]
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