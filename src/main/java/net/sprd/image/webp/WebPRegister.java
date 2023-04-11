/*
 * Copyright 2016 sprd.net AG (https://www.spreadshirt.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sprd.image.webp;

import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageWriterSpi;
import java.util.Iterator;

/**
 * @author ran
 */
public class WebPRegister {

    public static void registerImageTypes() {
        final Iterator<ImageWriterSpi> providers = IIORegistry.getDefaultInstance()
                .getServiceProviders(ImageWriterSpi.class, true);
        if (providers != null) {
            boolean found = false;
            while (providers.hasNext()) {
                final ImageWriterSpi next = providers.next();
                if (next.getClass().getName().equals(WebPImageWriterSpi.class.getName())) {
                    found = true;
                }
            }
            if (!found) {
                IIORegistry.getDefaultInstance().registerServiceProvider(new WebPImageWriterSpi());
            }
        }
    }

}