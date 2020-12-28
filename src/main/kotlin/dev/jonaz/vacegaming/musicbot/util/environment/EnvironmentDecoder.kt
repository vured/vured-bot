package dev.jonaz.vacegaming.musicbot.util.environment

import com.sksamuel.hoplite.*
import com.sksamuel.hoplite.decoder.Decoder
import com.sksamuel.hoplite.decoder.toValidated
import com.sksamuel.hoplite.fp.invalid
import kotlin.reflect.KType

class EnvironmentDecoder : Decoder<Environment> {
    override fun supports(type: KType) = type.classifier == Environment::class

    override fun decode(node: Node, type: KType, context: DecoderContext): ConfigResult<Environment> {
        return if (node is StringNode) {
            runCatching { enumValueOf<Environment>(node.value) }.toValidated {
                ConfigFailure.DecodeError(node, type)
            }
        } else {
            ConfigFailure.DecodeError(node, type).invalid()
        }
    }
}
