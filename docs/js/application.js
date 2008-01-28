/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For more information visit
 * http://architecturerules.googlecode.com/svn/docs/index.html
 */

includeJavaScript('js/prototype-1.5.1.1.js');
includeJavaScript('js/scriptaculous.js?load=effects');
includeJavaScript('js/sticker-1.0.js');
includeJavaScript('js/nifty.js');
includeJavaScript('http://syntaxhighlighter.googlecode.com/svn/tags/1.5.1/Scripts/shCore.js');
includeJavaScript('http://syntaxhighlighter.googlecode.com/svn/tags/1.5.1/Scripts/shBrushJava.js');
includeJavaScript('http://syntaxhighlighter.googlecode.com/svn/tags/1.5.1/Scripts/shBrushXml.js');
includeJavaScript('http://www.google-analytics.com/urchin.js');

function includeJavaScript(jsFile)
{
    document.write('<script type="text/javascript" src="' + jsFile + '"></script>');
}

window.onload = function() {
    TempalteHandler.loadRightNav();
    TempalteHandler.loadFooter();
    NiftyHandler.round();
    CodeHandler.highlight();
    AnalyticsHandler.load();

    setTimeout('NiftyHandler.roundFooter()', 500);
}

var TempalteHandler = {

    loadRightNav: function() {
        new Ajax.Updater('secondary', 'component_rightnav.html', {method: 'get', asynchronous: true, insertion: Insertion.Bottom });
    },

    loadFooter: function() {
        new Ajax.Updater('footer', 'component_footer.html', {method: 'get', asynchronous: true, insertion: Insertion.Bottom });
    }
}

var AnalyticsHandler = {

    load: function() {
        _uacct = "UA-616623-3";
        urchinTracker();
    }
}

var PrototypeHandler = {

    addZoomInFunction: function() {
        Element.addMethods({
            expandOnClick: function(element, tagName) {
                element = $(element);
                $(element).onclick = function() {
                    $('primary').style.overflow = 'visible';
                    $(tagName).style.width = '770px';
                    return Element.extend(tagName);
                }
            }
        });
    },

    addZoomOutFunction: function() {
        Element.addMethods({
            collapseOnClick: function(element, tagName) {
                element = $(element);
                $(element).onclick = function() {
                    $('primary').style.overflow = 'visible';
                    $(tagName).style.width = '520px';
                    return Element.extend(tagName);
                }
            }
        });
    }
}

var NiftyHandler = {

    round: function() {

        if (NiftyCheck())
        {
            Rounded("div#about", "#fff", "#E7EFFF");
            Rounded("div.downloadnow", "#fff", "#e8fecd");
            Rounded("div.recent-activity", "#eee", "#fff");
            /* not found usually*/
        }
    },

    roundFooter: function() {
        RoundedTop("div.footbar", "#fff", "#e8fecd");
    }
}

var CodeHandler = {

    highlight: function() {

        dp.SyntaxHighlighter.ClipboardSwf
                = 'http://syntaxhighlighter.googlecode.com/svn/tags/1.5.1/Scripts/clipboard.swf';
        dp.SyntaxHighlighter.HighlightAll('code');
    }

}

var Tour = {

    start: function() {

        var html = "";
        html += "<span onclick=\"Tour.close();\">close</span>"
        $('guided-tour-controls').innerHTML = html;

        new Effect.Puff('guided-tour-button');
        new Effect.BlindUp('downloadnow');
        new Effect.BlindDown('guided-tour');
        new Effect.BlindDown('guided-tour-controls');

        new Ajax.Updater('guided-tour', 'guided-tour.html', {method: 'get', asynchronous: true, insertion: Insertion.Top});

        sticker.totalscreens = 6;
        sticker.timeout = 10;

        setTimeout('sticker.loader();', 1000);
    },

    close: function() {

        new Effect.BlindUp('guided-tour');
        new Effect.BlindUp('guided-tour-controls');
        new Effect.BlindDown('downloadnow');
        new Effect.BlindDown('guided-tour-button');

        $('guided-tour').innerHTML = '';

        sticker.kill = true;
    },

    onComplete: function() {

        var html = "";
        html += "<a href=\"downloads.html;\">Download Now</a>"
        html += "<span onclick=\"Tour.close();\">close</span>"

        $('guided-tour-controls').innerHTML = html;
    }
}

var RSS = {

    onload: function() {

        var element;
        var googleGroupsUrl = 'http://groups.google.com/group/';

        element = $('users-recent-activity-header');
        element.style.cursor = 'pointer';
        element.onclick = function()
        {
            location.href
                    = googleGroupsUrl + 'architecture-rules-users/feed/rss_v2_0_topics.xml';
        }

        element = $('dev-recent-activity-header');
        element.style.cursor = 'pointer';
        element.onclick = function()
        {
            location.href
                    = googleGroupsUrl + 'architecture-rules-dev/feed/rss_v2_0_topics.xml';
        }

        element = $('activity-recent-activity-header');
        element.style.cursor = 'pointer';
        element.onclick = function()
        {
            location.href
                    = googleGroupsUrl + 'architecture-rules-activity/feed/rss_v2_0_topics.xml';
        }

        $('loading-mailing-lists').style.display = 'none';

        $('users-content').style.display = 'block';
        $('dev-content').style.display = 'block';
        $('activity-content').style.display = 'block';
    }
}

var Run = {

    makeZoomIn: function() {

        PrototypeHandler.addZoomInFunction();

        $('magnify_ant_task').expandOnClick('antTaskConfiguration');
        $('magnify_ant_unit_test').expandOnClick('antUnitTestConfiguration');
    },

    makeZoomOut: function() {

        PrototypeHandler.addZoomOutFunction();

        $('demagnify_ant_task').collapseOnClick('antTaskConfiguration');
        $('demagnify_ant_unit_test').collapseOnClick('antUnitTestConfiguration');
    }
}

var SampleConfiguration = {

    makeZoomIn: function() {

        PrototypeHandler.addZoomInFunction();

        $('magnify_configuration').expandOnClick('configrationXml');
    },

    makeZoomOut: function() {

        PrototypeHandler.addZoomOutFunction();

        $('demagnify_configuration').collapseOnClick('configrationXml');
    }
}
var MailingListHandler = {

    getUsersMailinglistContent: function() {
        return "<p class='newsAlt'><a class='newsLinkAlt' target='_mailinglist' href='http://groups.google.com/group/architecture-rules-users/browse_thread/thread/5e7936b1ccb3c343'>Release 2.0.1</a><br>2007-11-16, 12:48:36</p><p class='news'><a class='newsLink' target='_mailinglist' href='http://groups.google.com/group/architecture-rules-users/browse_thread/thread/01d273013b221884'>What JRE/JDK is needed? Where is this documented?</a><br>2007-11-16, 10:58:25</p><p class='newsAlt'><a class='newsLinkAlt' target='_mailinglist' href='http://groups.google.com/group/architecture-rules-users/browse_thread/thread/c7502f60f6e97114'>Announcement: 2.0-rc2 released + new Ant task</a><br>2007-11-13, 21:52:58</p><p class='news'><a class='newsLink' target='_mailinglist' href='http://groups.google.com/group/architecture-rules-users/browse_thread/thread/11c4e89bf4582f72'>update</a><br>2007-11-09, 20:33:40</p><p class='newsAlt'><a class='newsLinkAlt' target='_mailinglist' href='http://groups.google.com/group/architecture-rules-users/browse_thread/thread/f54e6f7d01361880'>1.1 released</a><br>2007-07-26, 9:46:39</p><p><span style='font-family: arial; font-size: 11px;'>Powered by: <a title='RSS-to-JavaScript.com: Free RSS to JavaScript Converter' href='http://www.rss-to-javascript.com' target='_powered' alt='Free Content for Virtually Any Web-site! RSS-to-Javascript.com'>RSS-to-JavaScript.com</a></span></p>"
    },

    getDevMailinglistContent: function () {
        return "<p class=newsAlt><a class=newsLinkAlt  target=_mailinglist  href='http://groups.google.com/group/architecture-rules-dev/browse_thread/thread/5e7936b1ccb3c343'>Release 2.0.1</a><br>2007-11-16, 11:48:36</p><p class=news><a class=newsLink  target=_mailinglist  href='http://groups.google.com/group/architecture-rules-dev/browse_thread/thread/c7502f60f6e97114'>Announcement: 2.0-rc2 released + new Ant task</a><br>2007-11-13, 20:52:58</p><p class=newsAlt><a class=newsLinkAlt  target=_mailinglist  href='http://groups.google.com/group/architecture-rules-dev/browse_thread/thread/11c4e89bf4582f72'>update</a><br>2007-11-09, 19:33:40</p><p class=news><a class=newsLink  target=_mailinglist  href='http://groups.google.com/group/architecture-rules-dev/browse_thread/thread/97de6102eb04612b'>2.0-rc1 release</a><br>2007-11-01, 21:49:57</p><p class=newsAlt><a class=newsLinkAlt  target=_mailinglist  href='http://groups.google.com/group/architecture-rules-dev/browse_thread/thread/10534921daf22058'>To have an ant task to run architecturerules ?</a><br>2007-10-31, 23:36:01</p></span><p><span style='font-family: arial; align: left; font-size: 11px;'>Powered by: <a title='RSS-to-JavaScript.com: Free RSS to JavaScript Converter' href=http://www.rss-to-javascript.com target=_powered alt='Free Content for Virtually Any Web-site! RSS-to-Javascript.com'>RSS-to-JavaScript.com</a></span></p>"
    },

    getActivityMailinglistContent: function() {
        return "<p class=newsAlt><a class=newsLinkAlt  target=_mailinglist  href='http://groups.google.com/group/architecture-rules-activity/browse_thread/thread/18643d24a95e933a'>[architecturerules commit] r213 - docs trunk</a><br>2007-11-17, 7:40:05</p><p class=news><a class=newsLink  target=_mailinglist  href='http://groups.google.com/group/architecture-rules-activity/browse_thread/thread/8d135c67971de6f1'>[architecturerules commit] r212 - docs</a><br>2007-11-16, 11:59:41</p><p class=newsAlt><a class=newsLinkAlt  target=_mailinglist  href='http://groups.google.com/group/architecture-rules-activity/browse_thread/thread/56348fb5bdc86d42'>[architecturerules commit] r211 - in docs: . css</a><br>2007-11-16, 11:54:40</p><p class=news><a class=newsLink  target=_mailinglist  href='http://groups.google.com/group/architecture-rules-activity/browse_thread/thread/d76b478107ea379c'>[architecturerules commit] r210 - docs</a><br>2007-11-16, 11:50:28</p><p class=newsAlt><a class=newsLinkAlt  target=_mailinglist  href='http://groups.google.com/group/architecture-rules-activity/browse_thread/thread/ecf28c8af3d2e8a1'>[architecturerules commit] r209 - docs</a><br>2007-11-16, 11:46:27</p></span><p><span style='font-family: arial; align: left; font-size: 11px;'>Powered by: <a title='RSS-to-JavaScript.com: Free RSS to JavaScript Converter' href=http://www.rss-to-javascript.com target=_powered alt='Free Content for Virtually Any Web-site! RSS-to-Javascript.com'>RSS-to-JavaScript.com</a></span></p>"
    }
}