/*
 * Copyright (c) 2012, Metron, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Metron, Inc. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL METRON, INC. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.metsci.glimpse.charts.vector.parser.attributes;

import java.io.DataOutputStream;
import java.io.IOException;

import com.metsci.glimpse.charts.vector.parser.autogen.ENCAttributeType;

import java.io.DataInput;

public class ENCCodedAttribute extends ENCAbstractAttribute{
    private String attributeValue;

    public ENCCodedAttribute(){}

    public String getENCAttributeValue(){
        return attributeValue;
    }

    public void setENCAttributeValue(String value){
        attributeValue 	= value;
    }

    @Override
    public ENCAttributeClass getAttributeClass() {
        return ENCAttributeClass.Coded;
    }

    @Override
    public Object getAttributeValueAsObject() {
        if (attributeValue == null)
            return null;
        else
            return attributeValue;
    }

    public String getAttributeValueAsString() {
        if (attributeValue == null)
            return null;
        else
            return attributeValue;
    }

    protected void write0(DataOutputStream fout) throws IOException {
        ENCAttributeType.write(fout, getAttributeType());

        if(attributeValue == null){
            fout.writeBoolean(false);
        }
        else{
            fout.writeBoolean(true);
            fout.writeInt(attributeValue.length());
            char[] _value = attributeValue.toCharArray();

            for(int i = 0, n = _value.length; i < n; i++)
                fout.writeChar(_value[i]);
        }
    }

    public static ENCCodedAttribute read(DataInput fin) throws IOException{
        ENCCodedAttribute _retVal = new ENCCodedAttribute();

        _retVal.setAttributeType(ENCAttributeType.read(fin));

        boolean _haveValue = fin.readBoolean();
        if(_haveValue){
            int _length = fin.readInt();
            char[] _value = new char[_length];
            for(int i = 0; i < _length; i++)
                _value[i] = fin.readChar();

            _retVal.setENCAttributeValue(new String(_value));
        }
        _retVal.isNull = ! _haveValue;

        return _retVal;
    }
}
