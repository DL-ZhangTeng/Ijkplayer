/*
 * Copyright 2017 jiajunhui<junhui_jia@163.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.kk.taurus.playerbase.render;

import java.io.Serializable;

/**
 * Created by Taurus on 2016/10/18.
 */

public enum AspectRatio implements Serializable {
    AspectRatio_16_9,
    AspectRatio_4_3,
    AspectRatio_MATCH_PARENT,
    AspectRatio_FILL_PARENT,
    AspectRatio_FIT_PARENT,
    AspectRatio_ORIGIN,
    AspectRatio_FILL_WIDTH,
    AspectRatio_FILL_HEIGHT
}
