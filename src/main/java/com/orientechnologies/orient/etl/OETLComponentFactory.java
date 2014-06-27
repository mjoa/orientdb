/*
 *
 *  * Copyright 2010-2014 Orient Technologies LTD (info(at)orientechnologies.com)
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.orientechnologies.orient.etl;

import com.orientechnologies.common.log.OLogManager;
import com.orientechnologies.orient.etl.extract.OExtractor;
import com.orientechnologies.orient.etl.extract.OFileExtractor;
import com.orientechnologies.orient.etl.extract.OLineExtractor;
import com.orientechnologies.orient.etl.loader.OLoader;
import com.orientechnologies.orient.etl.loader.OOrientLoader;
import com.orientechnologies.orient.etl.transform.OCSVTransformer;
import com.orientechnologies.orient.etl.transform.OJSONTransformer;
import com.orientechnologies.orient.etl.transform.ONullTransformer;
import com.orientechnologies.orient.etl.transform.OTransformer;

import java.util.HashMap;
import java.util.Map;

/**
 * ETL component factory. Registers all the ETL components.
 * 
 * @author Luca Garulli (l.garulli-at-orientechnologies.com)
 */
public class OETLComponentFactory {
  protected final Map<String, Class<? extends OExtractor>>   extractors   = new HashMap<String, Class<? extends OExtractor>>();
  protected final Map<String, Class<? extends OTransformer>> transformers = new HashMap<String, Class<? extends OTransformer>>();
  protected final Map<String, Class<? extends OLoader>>      loaders      = new HashMap<String, Class<? extends OLoader>>();

  public OETLComponentFactory() {
    registerExtractor(OFileExtractor.class);
    registerExtractor(OLineExtractor.class);

    registerTransformer(OJSONTransformer.class);
    registerTransformer(OCSVTransformer.class);
    registerTransformer(ONullTransformer.class);

    registerLoader(OOrientLoader.class);
  }

  public OETLComponentFactory registerExtractor(final Class<? extends OExtractor> iComponent) {
    try {
      extractors.put(iComponent.newInstance().getName(), iComponent);
    } catch (Exception e) {
      OLogManager.instance().error(this, "Error on registering extractor: %s", iComponent.getName());
    }
    return this;
  }

  public OETLComponentFactory registerTransformer(final Class<? extends OTransformer> iComponent) {
    try {
      transformers.put(iComponent.newInstance().getName(), iComponent);
    } catch (Exception e) {
      OLogManager.instance().error(this, "Error on registering transformer: %s", iComponent.getName());
    }
    return this;
  }

  public OETLComponentFactory registerLoader(final Class<? extends OLoader> iComponent) {
    try {
      loaders.put(iComponent.newInstance().getName(), iComponent);
    } catch (Exception e) {
      OLogManager.instance().error(this, "Error on registering loader: %s", iComponent.getName());
    }
    return this;
  }

}
