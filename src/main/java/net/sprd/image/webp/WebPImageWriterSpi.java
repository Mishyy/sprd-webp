/*
 * Copyright 2013 Luciad (http://www.luciad.com)
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

import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.util.Locale;

/**
 * @author ran
 */
public class WebPImageWriterSpi extends ImageWriterSpi {
    public WebPImageWriterSpi() {
        super(
                "sprd.net",
                "1.0",
                new String[]{"WebP", "webp"},
                new String[]{"webp"},
                new String[]{"image/webp"},
                WebPWriter.class.getName(),
                new Class[]{ImageOutputStream.class},
                new String[]{WebPImageWriterSpi.class.getName()},
                false,
                null,
                null,
                null,
                null,
                false,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public boolean canEncodeImage(ImageTypeSpecifier type) {
        ColorModel colorModel = type.getColorModel();
        SampleModel sampleModel = type.getSampleModel();
        int transferType = sampleModel.getTransferType();

        if (colorModel instanceof ComponentColorModel) {
            if (!(sampleModel instanceof ComponentSampleModel)) {
                return false;
            }

            if (transferType != DataBuffer.TYPE_BYTE && transferType != DataBuffer.TYPE_INT) {
                return false;
            }
        } else if (colorModel instanceof DirectColorModel) {
            if (!(sampleModel instanceof SinglePixelPackedSampleModel)) {
                return false;
            }

            if (transferType != DataBuffer.TYPE_INT) {
                return false;
            }
        }

        ColorSpace colorSpace = colorModel.getColorSpace();
        if (!(colorSpace.isCS_sRGB())) {
            return false;
        }

        int[] sampleSize = sampleModel.getSampleSize();
        for (final int j : sampleSize) {
            if (j > 8) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ImageWriter createWriterInstance(Object extension) {
        return new WebPWriter(this);
    }

    @Override
    public String getDescription(Locale locale) {
        return "WebP Writer";
    }

}