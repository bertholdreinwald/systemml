/**
 * (C) Copyright IBM Corp. 2010, 2015
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
 * 
 */


package org.apache.sysml.runtime.matrix.operators;

import java.io.Serializable;

import org.apache.sysml.hops.Hop.OpOp2;
import org.apache.sysml.runtime.functionobjects.And;
import org.apache.sysml.runtime.functionobjects.Builtin;
import org.apache.sysml.runtime.functionobjects.Divide;
import org.apache.sysml.runtime.functionobjects.Equals;
import org.apache.sysml.runtime.functionobjects.GreaterThan;
import org.apache.sysml.runtime.functionobjects.GreaterThanEquals;
import org.apache.sysml.runtime.functionobjects.IntegerDivide;
import org.apache.sysml.runtime.functionobjects.LessThan;
import org.apache.sysml.runtime.functionobjects.LessThanEquals;
import org.apache.sysml.runtime.functionobjects.Minus;
import org.apache.sysml.runtime.functionobjects.MinusNz;
import org.apache.sysml.runtime.functionobjects.Modulus;
import org.apache.sysml.runtime.functionobjects.Multiply;
import org.apache.sysml.runtime.functionobjects.NotEquals;
import org.apache.sysml.runtime.functionobjects.Or;
import org.apache.sysml.runtime.functionobjects.Plus;
import org.apache.sysml.runtime.functionobjects.Power;
import org.apache.sysml.runtime.functionobjects.ValueFunction;
import org.apache.sysml.runtime.functionobjects.Builtin.BuiltinFunctionCode;

public class BinaryOperator  extends Operator implements Serializable
{

	private static final long serialVersionUID = -2547950181558989209L;

	public ValueFunction fn;
	
	public BinaryOperator(ValueFunction p)
	{
		fn=p;
		//as long as (0 op 0)=0, then op is sparseSafe
		if(fn instanceof Plus || fn instanceof Multiply || fn instanceof Minus 
				|| fn instanceof And || fn instanceof Or)
			sparseSafe=true;
		else
			sparseSafe=false;
	}
	
	/**
	 * Method for getting the hop binary operator type for a given function object.
	 * This is used in order to use a common code path for consistency between 
	 * compiler and runtime.
	 * 
	 * @return
	 */
	public OpOp2 getBinaryOperatorOpOp2()
	{
		if( fn instanceof Plus )				return OpOp2.PLUS;
		else if( fn instanceof Minus )			return OpOp2.MINUS;
		else if( fn instanceof Multiply )		return OpOp2.MULT;
		else if( fn instanceof Divide )			return OpOp2.DIV;
		else if( fn instanceof Modulus )		return OpOp2.MODULUS;
		else if( fn instanceof IntegerDivide )	return OpOp2.INTDIV;
		else if( fn instanceof LessThan )		return OpOp2.LESS;
		else if( fn instanceof LessThanEquals )	return OpOp2.LESSEQUAL;
		else if( fn instanceof GreaterThan )	return OpOp2.GREATER;
		else if( fn instanceof GreaterThanEquals )	return OpOp2.GREATEREQUAL;
		else if( fn instanceof Equals )			return OpOp2.EQUAL;
		else if( fn instanceof NotEquals )		return OpOp2.NOTEQUAL;
		else if( fn instanceof And )			return OpOp2.AND;
		else if( fn instanceof Or )				return OpOp2.OR;
		else if( fn instanceof Power )			return OpOp2.POW;
		else if( fn instanceof MinusNz )		return OpOp2.MINUS_NZ;
		else if( fn instanceof Builtin ) {
			BuiltinFunctionCode bfc = ((Builtin) fn).getBuiltinFunctionCode();
			if( bfc == BuiltinFunctionCode.MIN ) 		return OpOp2.MIN;
			else if( bfc == BuiltinFunctionCode.MAX ) 	return OpOp2.MAX;
			else if( bfc == BuiltinFunctionCode.LOG ) 	return OpOp2.LOG;
			else if( bfc == BuiltinFunctionCode.LOG_NZ ) return OpOp2.LOG_NZ;
		}
		
		//non-supported ops (not required for sparsity estimates):
		//PRINT, CONCAT, QUANTILE, INTERQUANTILE, IQM, 
		//CENTRALMOMENT, COVARIANCE, APPEND, SEQINCR, SOLVE, MEDIAN,
			
		return OpOp2.INVALID;
	}
}