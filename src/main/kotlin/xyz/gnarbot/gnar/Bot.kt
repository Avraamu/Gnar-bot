package xyz.gnarbot.gnar

import net.dv8tion.jda.core.*
import net.dv8tion.jda.core.entities.Game
import net.dv8tion.jda.core.utils.SimpleLog
import xyz.gnarbot.gnar.servers.Shard
import xyz.gnarbot.gnar.utils.*
import java.awt.Color
import java.util.Date
import java.util.concurrent.Executors
import kotlin.jvm.JvmStatic as static

/**
 * Main class of the bot. Implemented as a singleton.
 */
object Bot
{
    @static val color = Color(0, 80, 175)
    
    @static val LOG = SimpleLog.getLog("Bot")!!
    
    @static val token = "_" //default token
    
    @static val files = BotFiles()
    
    var initialized = false
        private set
    
    /** Sharded JDA instances of the bot.*/
    val shards = mutableListOf<Shard>()
    
    /** Administrator users of the bot. */
    val admins = hashSetOf<String>().apply {
        addAll(files.admins.readLines())
    }
    
    val startTime = System.currentTimeMillis()
    val scheduler = Executors.newSingleThreadScheduledExecutor()!!
    
    val authTokens = files.tokens.readProperties()
    
    fun start(token : String, numShards : Int)
    {
        if (initialized) throw IllegalStateException("Bot instance have already been initialized.")
        initialized = true
        
        LOG.info("Initializing Bot.")
        LOG.info("Requesting $numShards shards.")
        
        LOG.info("There are ${admins.size} administrators registered for the bot.")
        
        for (id in 0 .. numShards - 1)
        {
            val jda = JDABuilder(AccountType.BOT).run {
                if (numShards > 1) useSharding(id, numShards)
                
                setToken(token)
                setAutoReconnect(true)
                setGame(Game.of("Shard: $id | _help"))
                
                buildBlocking()
            }
            
            jda.selfUser.manager.setName("Gnar").queue()
            
            shards += Shard(id, jda)
            
            LOG.info("Built shard $id.")
        }
        
        LOG.info("Bot is now connecting to Discord.")
        Utils.setLeagueInfo()
    }
    
    fun stop()
    {
        shards.forEach(Shard::shutdown)
        initialized = false
        
        LOG.info("Bot is now disconnecting from Discord.")
    }
    
    val uptime : String
        get()
        {
            val s = (Date().time - startTime) / 1000
            val m = s / 60
            val h = m / 60
            val d = h / 24
            return "$d days, ${h % 24} hours, ${m % 60} minutes and ${s % 60} seconds"
        }
    
    val simpleUptime : String
        get()
        {
            val s = (Date().time - startTime) / 1000
            val m = s / 60
            val h = m / 60
            val d = h / 24
            return "${d}d ${h % 24}h ${m % 60}m ${s % 60}s"
        }
}