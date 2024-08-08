package com.gamstrain.oppeningapp.di

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.gamstrain.oppeningapp.localData
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object AppDataSerializer : Serializer<localData> {

    override val defaultValue: localData = localData.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): localData {
        try {
            return localData.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: localData, output: OutputStream) =
        t.writeTo(output)
}