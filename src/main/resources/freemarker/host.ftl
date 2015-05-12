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

    <#list hostForestList?keys as key>
        <#assign items = hostForestList[key]>
        <div class="panel panel-default">
            <div class="panel-heading">
                <strong>Forest:</strong> <small>${key}</small>
                <span class="pull-right clickable"><i class="glyphicon glyphicon-chevron-up"></i></span>
            </div>
            <div class="panel-body">
                <div id="hostchart${key_index}" class="chart"></div>
                <div class="row">
                    <div class="col-md-3"><a href="#" class="btn btn-primary savePNG disabled"><span class="glyphicon glyphicon-download" aria-hidden="true"></span> Download chart as PNG image</a></div>
                    <div class="col-md-3"><a href="#" class="btn btn-primary disabled"><span class="glyphicon glyphicon-download" aria-hidden="true"></span> TODO: Download chart as SVG image</a></div>
                </div>
            </div>
        </div>
    </#list>
    <hr class="bottom-spaced"/>

</div>
<#include "footer.ftl">
</body>
</html>

<script>
    $(function() {
    $(document).on('click', '.panel-heading span.clickable', function(e){
        var $this = $(this);
        if(!$this.hasClass('panel-collapsed')) {
            $this.parents('.panel').find('.panel-body').slideUp();
            $this.addClass('panel-collapsed');
            $this.find('i').removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
        } else {
            $this.parents('.panel').find('.panel-body').slideDown();
            $this.removeClass('panel-collapsed');
            $this.find('i').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
        }
    });
    <#list hostForestList?keys as key>
        <#assign items = hostForestList[key]>
            var chart${key_index} = c3.generate({
                bindto: '#hostchart${key_index}',
                size: { width: 1100, height: 500 },
                data: {
                    type: 'spline',
                    x: 'x',
                    columns: [
                        ['x', <#list items as item>'${item.getDate()}',</#list>],
                        ['Nascent Fragments', <#list items as item>${item.getNascentFragments()}, </#list>],
                        ['Deleted Fragments', <#list items as item>${item.getDeletedFragments()}, </#list>],
                        ['Orphaned Fragments', <#list items as item>${item.getOrphanedProperties()}, </#list>]
                    ]
                },
                axis: {
                    x: {
                        type: 'timeseries',
                        tick: { format: '%Y-%m-%d'}
                    }
                }
            });
    </#list>
    });
</script>