package xyz.gnarbot.gnar.commands.executors.music.dj

import xyz.gnarbot.gnar.commands.executors.music.parent.MusicExecutor
import xyz.gnarbot.gnar.commands.handlers.Command
import xyz.gnarbot.gnar.members.Level
import xyz.gnarbot.gnar.utils.Note

@Command(aliases = arrayOf("stop"),
        level = Level.DJ,
        description = "Stop and clear the music player.",
        symbol = "♬")
class StopCommand : MusicExecutor() {

    override fun execute(note: Note, args: List<String>) {
        val manager = servlet.musicManager

        manager.scheduler.queue.clear()
        manager.player.stopTrack()
        manager.player.isPaused = false
        servlet.audioManager.closeAudioConnection()

        note.respond().embed("Stop Playback") {
            color = musicColor
            description = "Playback has been completely stopped and the queue has been cleared."
        }.rest().queue()
    }
}
