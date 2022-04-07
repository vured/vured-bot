package dev.jonaz.vured.bot.service.discord

import dev.jonaz.vured.bot.control.ControlMessageCase
import dev.jonaz.vured.bot.control.button.Button
import dev.jonaz.vured.bot.control.button.ButtonHandler
import dev.jonaz.vured.bot.control.button.ButtonModel
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle
import net.dv8tion.jda.api.interactions.components.buttons.Button as ButtonComponent
import org.atteo.classindex.ClassIndex

class ButtonService {
    private val buttons = mutableListOf<ButtonModel>()

    fun getButtons(messageCase: ControlMessageCase): List<ButtonModel> {
        return buttons.filter { it.messageCase == messageCase }
    }

    fun addButtons(message: Message, buttons: List<ButtonModel>) {
        val buttonComponents = buttons.map {
            getComponentFromButton(it)
        }

        message.editMessage(message).setActionRow(buttonComponents).queue()
    }

    fun initButtons() {
        val annotation = Button::class.java
        val classes = ClassIndex.getAnnotated(annotation)

        classes.forEach { clazz ->
            val annotationData = clazz.getAnnotation(annotation)

            ButtonModel(
                order = annotationData.order,
                identifier = annotationData.identifier,
                messageCase = annotationData.messageCase,
                style = annotationData.style,
                content = annotationData.content,
                clazz = clazz
            ).also(buttons::add)
        }

        buttons.sortBy { it.order }
    }

    fun execute(event: ButtonInteractionEvent) {
        buttons.findLast { it.identifier == event.componentId }?.run {
            val method = clazz.getMethod(ButtonHandler::execute.name, ButtonInteractionEvent::class.java)
            val instance = clazz.getDeclaredConstructor().newInstance()

            method.invoke(instance, event)
        }
    }

    private fun getComponentFromButton(button: ButtonModel) = when (button.style) {
        ButtonStyle.SUCCESS -> ButtonComponent.success(button.identifier, button.content)
        ButtonStyle.SECONDARY -> ButtonComponent.secondary(button.identifier, button.content)
        ButtonStyle.LINK -> ButtonComponent.link(button.identifier, button.content)
        ButtonStyle.DANGER -> ButtonComponent.danger(button.identifier, button.content)
        else -> ButtonComponent.primary(button.identifier, button.content)
    }
}
