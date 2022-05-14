const mineflayer = require('mineflayer');
const MPPClient = require('./MPPClient.js');
let client = new MPPClient(null,null,"MPPClone token");
let opt = require('./acc.json');
const bot = mineflayer.createBot(opt);
let host = opt.port==25565?opt.host:opt.host+`:${opt.port}`;
bot.on('chat', (username, message) => {
  if (username === bot.username) return;
  if(username=='you' || username=="SimpleTpa" || username=="is") return;
  else client.say(`[${host}] ${username}> ${message}`);
});
// TODO: bot.version, wget?
let advancements = require('./language.json'); // https://raw.githubusercontent.com/PrismarineJS/minecraft-data/master/data/pc/1.__/language.json
bot.on('whisper',(j1,j2,j3,jmsg,j4)=>{
  let user = jmsg.json.with[0].insertion
  let msg = jmsg.json.with[1].text;
  if(user!=="BestLinux") return;
  console.log(msg);
  if(msg.startsWith('minecraft:')) {
    let content = msg.replace(' ','').replace(RegExp('\\/','g'),'.').replace('minecraft:','advancements.').split(',');
    client.say(`[${host}] ${content[1]} has made the advancement ${advancements[content[0]+'.title']}`);
  } else client.say([`${host}] ${msg}`);
});
client.on('a',msg=>{
  if(msg.p.id!==client.getOwnParticipant().id) bot.chat('/pmppc '+JSON.stringify({text:`[${msg.p.id.substring(0,6)}] ${msg.p.name}> ${msg.a.toString().substring(0,511)}`,color:msg.p.color}));
});
bot.on('playerJoined',player=>{
  client.say(`[${host}] ${player.username} joined the game`);
});
bot.on('playerLeft',player=>{
  client.say(`[${host}] ${player.username} left the game`);
});
MPP.on('mc',msg=>bot.chat('/pmppc '+msg));
bot.on('error', console.error);
bot.on('kicked', console.warn);
bot.on('error',()=>setTimeout(()=>process.exit(1),50));
bot.on('kicked',()=>setTimeout(()=>process.exit(1),20000));
client.setChannel("Minecraft");
client.start();
