# Invite guard

## motivation

Often when you play minecraft server some ill-wishes persons can appear and spoil entire gameplay.
In such case you can try to sweep server under the carpet -- set up firewall, use whitelist etc. 
But all this approaches have significant disadvantages.
For example talking about whitelist we instantly stumbleupon few problems:
 - it's nearly non-scalable, if you want to connect it to some service it would be very challenging
 - for newcomers it's hard to get in touch with players to ask them .
 - hard to localize

So invite guard intended to solve this prolems.

## how to use

## Todo list
- [x] auto delete old records
- [ ] turn to state based dispatching since it's much simple and less error prone  
- [ ] make more configurable
    - [ ] make use of permissions
    - [ ] actions (kick/redirect/message/etc)
- [x] multi client servers
- [ ] improve command interface
- [x] make more fault tolerant
    - by that i mean try to restore connection when one of servers fall down
- [ ] make log messages more clear 
- [ ] make separate server for that  
- [ ] make it spam less(make logging more slient or remove action traffic)

## Probems


- <strike>title blinking</strike>
- <strike>warnging on login</strike>

