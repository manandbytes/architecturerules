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


/*
 * This script is a derivative of sticker-0.1 by Vladimir Cvetic
 * available at http://www.knowbies.com/js-news-sticker
 *
 * Changes were made to change the bahavior in order to have the
 * graphic change after the slider slides up, but before the slider
 * slides back down.
 */
sticker = new Object;

sticker = {

    loader: function() {

        sticker.kill = false;

        sticker.preloadImages();

        if (!sticker.timeout)
        {
            sticker.timeout = 5000;
        }
        else
        {
            sticker.timeout = sticker.timeout * 1000;
        }

        if (!sticker.totalscreens)
        {
            sticker.totalscreens = 9999;
        }

        setTimeout('sticker.isLoaded()', 1000);
    },

    preloadImages: function() {

        backgroundImg = new Image();
        backgroundImg.src = 'images/bg.png';

        sticker.preload = $$('div[id="bg_(.*)"]');
        sticker.img = new Array();

        for (i = 0; i < sticker.preload.length; i++)
        {
            sticker.img[i] = new Image();
            sticker.img[i].src = sticker.preload[i].innerHTML;
        }
    },

    isLoaded: function () {

        var result = true;

        for (i = 0; i < sticker.preload.length; i++)
        {
            if (!sticker.img[i].complete)
            {
                result = false;
            }
        }

        if (!result)
        {
            setTimeout('sticker.isLoaded()', 1000);
        }
        else
        {
            $('wrap').setStyle({background: ''});
            sticker.featured(-1);
            return true;
        }
    },

    blindItUp: function () {
        new Effect.BlindUp('text');
    },

    blindIdDown: function () {
        sticker.addContent();
        new Effect.BlindDown('text');
    },

    addContent: function () {
        $('featuredHead').innerHTML = $('title_' + this.id).innerHTML;
        $('featuredText').innerHTML = $('text_' + this.id).innerHTML;
    },

    featured: function(vari) {

        if (vari == -1)
        {
            $('wrap').setStyle({background: '#333 url(' + $('bg_' + 1).innerHTML + ') no-repeat center'});
        }

        storyList = $$('#featured li');
        count = storyList.length;

        if (vari == -1 || vari == count)
        {
            id = 1;
        }
        else
        {
            id = vari + 1;
        }

        sticker.id = id;

        if (vari != -1)
        {
            sticker.blindItUp();

            setTimeout("$('wrap').setStyle({background: '#333 url('+$('bg_'+this.id).innerHTML+') no-repeat center'})", 1100);
            setTimeout('sticker.blindIdDown()', 1500);
        }
        else
        {
            sticker.addContent();
            sticker.blindIdDown();
        }

        if (id < sticker.totalscreens && !sticker.kill)
        {
            setTimeout('sticker.featured(' + id + ')', sticker.timeout);
        }
        else {
            setTimeout('sticker.onComplete()', 3000);
        }
    },

    onComplete: function() {

        /**
         * @see application.js
         */
        Tour.onComplete();
    }
}