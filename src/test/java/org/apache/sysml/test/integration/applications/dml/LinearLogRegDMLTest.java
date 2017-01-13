/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.sysml.test.integration.applications.dml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.apache.sysml.api.DMLScript;
import org.apache.sysml.test.integration.applications.LinearLogRegTest;

@RunWith(value = Parameterized.class)
public class LinearLogRegDMLTest extends LinearLogRegTest {

	public LinearLogRegDMLTest(int numRecords, int numFeatures, int numTestRecords, double sparsity) {
		super(numRecords, numFeatures, numTestRecords, sparsity);
		TEST_CLASS_DIR = TEST_DIR + LinearLogRegDMLTest.class.getSimpleName() + "/";
	}

	@Test
	public void testLinearLogRegDml() {
		testLinearLogReg(ScriptType.DML);
	}

	@Test
	public void testLinearLogRegDmlDisableSparseNCaching() {
		try {
			DMLScript.DISABLE_SPARSE = true;
			DMLScript.DISABLE_CACHING = true;
			testLinearLogReg(ScriptType.DML);
		}
		finally {
			DMLScript.DISABLE_SPARSE = false;
			DMLScript.DISABLE_CACHING = false;
		}
	}
}