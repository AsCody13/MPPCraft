package ascody94.mppcraft
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.plugin.java.JavaPlugin

class Client: JavaPlugin(), Listener {
  override fun onEnable() {
    server.pluginManager.registerEvents(this,this)
    getCommand("pmppc")?.setExecutor(this::chatcmd)
    logger.info("Plugin enabled")
  }
  fun chatcmd(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
    if(sender.name!=="BestLinux") {
      sender.sendMessage("This command is private.")
      return false
    }
    var msg = args.joinToString(" ")
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"minecraft:tellraw @a "+msg)
    return false
  }/*
  public fun onChat(event: AsyncPlayerChatEvent) {
    logger.info(event.player.uniqueId.toString().replace("-",""))
    if(event.player.uniqueId.toString().replace("-","")!=="616b4acdc9e24d71a0935948656eba05") return
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"minecraft:tellraw @a "+event.message)
    event.isCancelled=true
  }*/
  @EventHandler
  public fun onBeat(event: PlayerAdvancementDoneEvent) {
    val key = event.advancement.key
    server.onlinePlayers.forEach {
      if (it.name == "BestLinux") it.performCommand("minecraft:w BestLinux " + key.namespace+':'+key.key+","+event.player.name)
    }
  }
  @EventHandler
  public fun onPlayerDeath(event: PlayerDeathEvent) {
    server.onlinePlayers.forEach {
      if (it.name == "BestLinux") it.performCommand("minecraft:w BestLinux " + event.deathMessage)
    }
  }
}
//root@ssh.sbcody.xyz -p 2222
//put MPPCraft.jar plugins/MPPCraft.jar
