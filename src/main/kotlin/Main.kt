import kotlin.math.abs

/**
 * You can edit, run, and share this code.
 * play.kotlinlang.org
 */
//import java.util.Scanner
fun main() {
    val userPlayer = Player(true, "You")
    val creatures = ArrayList<Creature>()
    val stuff = ArrayList<Item>()
    val inventory = ArrayList<Item>()
    val userCreatures = ArrayList<Creature>()
    val basic = Creature("Tradesman",4,1,4,2,3,2,"A skilled worker with a good amount of knowledge")
    val warrior = Creature("Fighter",5,1,4,3,2,1,"a strong human used to combat and fighting")
    //val mage = Creature("Mage",3,0,3,3,5,3,"a magical human who can cast spells")

    //var devtools = false
    //println("Activate devtools? Type 'Yes' to do so")
   // val tools = readln()
    /*if(!devtools) {
        if (tools == "Yes ") {
            println("Devtools active")
            devtools=true
        }else if(tools == "Yes"){
            println("Haha, u thought")
        }else{
            println("devtools inactive")
        }
    }*/
    println("Welcome to THE GAME, Select which creature you want to play as!")
    val randomCreatureList = ArrayList<Creature>()
    val randomWeaponList = ArrayList<Item>()
    val randomArmorList = ArrayList<Item>()
    inventory.add(Sword())
    inventory.add(Spear())
    inventory.add(Bow())

    stuff.add(Sword())
    stuff.add(Spear())
    stuff.add(GreatAxe())

    inventory.add(GreatAxe())
    inventory.add(TomeOfUndeath())
    inventory.add(ClericsStaff())
    inventory.add(BookOfFlames())
    inventory.add(MartialCombat())

    stuff.add(LightArmor())
    stuff.add(Cloak())

    inventory.add(LightArmor())
    inventory.add(HeavyArmor())
    inventory.add(MageRobes())
    inventory.add(MartialArmor())
    inventory.add(Cloak())

    inventory.add(BloodRing())
    inventory.add(RingOfKnowledge())
    inventory.add(RingOfFortitude())
    inventory.add(RingOfSpeed())
    inventory.add(RingOfCriticalHits())
    inventory.add(RingOfHealth())
    inventory.add(RingOfMagic())

    userPlayer.inventory = inventory

    randomWeaponList.add(Sword())
    randomWeaponList.add(Spear())
    randomWeaponList.add(Bow())
    randomWeaponList.add(GreatAxe())
    randomArmorList.add(LightArmor())
    randomArmorList.add(HeavyArmor())
    randomArmorList.add(MageRobes())
    randomArmorList.add(MartialArmor())
    randomArmorList.add(Cloak())
    //randomWeaponList.add(BookOfFlames())
    randomWeaponList.add(MartialCombat())

    userCreatures.add(basic)
    userCreatures.add(warrior)
    userCreatures.add(Mage())
    userCreatures.add(Rogue())

    creatures.add(basic)
    creatures.add(warrior)
    creatures.add(Monk())
    creatures.add(Rogue())
    creatures.add(Goblin())
    creatures.add(Troll())
    creatures.add(Skeleton())
    creatures.add(Mage())

    randomCreatureList.add(warrior)
    randomCreatureList.add(Monk())
    randomCreatureList.add(Rogue())
    randomCreatureList.add(Goblin())
    randomCreatureList.add(Troll())
    randomCreatureList.add(Skeleton())

    chooseCreature(userPlayer,creatures)
    userPlayer.chooseItem()

    println("You enter a strange dungeon!")

    var delve = ""
    while(delve!="STOP" && userPlayer.gold>=0){
        val enemy = Player(false,"Enemy")
        val randomWeapon = (0..<randomWeaponList.size).random()
        val randomArmor = (0..<randomArmorList.size).random()
        val randomCreature = (0..<randomCreatureList.size).random()
        enemy.addItem(randomWeaponList.get(randomWeapon))
        enemy.addItem(randomArmorList.get(randomArmor))
        enemy.newCreature(randomCreatureList.get(randomCreature))
        println(enemy.getDescription(false) + " appears suddenly!")
        val win = playerVNPC(userPlayer,enemy)
        if(enemy.getArmor(true)=="Cloak"){
            println("The enemy turned out to be " + enemy.getDescription(true))
        }
        //val randGold = (1..5).random()

        val randGold = enemy.getGearPrice()

        if(win==userPlayer.name){
            userPlayer.gold+=randGold
            println("You win! You gain $randGold gold! You now have " + userPlayer.gold + " gold!")
            println("delve further? Type 'STOP' to leave the dungeon")
            delve = readln()
        }else if(userPlayer.gold-randGold>-1){
            userPlayer.gold-=randGold
            println("You lose! The creature stole $randGold gold from you and ran away into the dungeon. You have " + userPlayer.gold + " gold left...")
        }else{
            userPlayer.gold-=randGold
            println("You've lost all your money, you leave the dungeon... defeated")
            break
        }
        shop(userPlayer, inventory)
        userPlayer.chooseItem()
        chooseCreature(userPlayer, userCreatures)
    }
    println("Thanks for playing!")
}

fun shop(user: Player, allItems: ArrayList<Item>){
    var finishedChoosing = "NO"
    var itemExists = false
    val limitedItems = ArrayList<Item>()
    for(item in allItems){
        var itemDNE = true
        for(userItem in user.inventory){
            if(userItem.name == item.name){
                itemDNE = false
            }
        }
        if(itemDNE){
            limitedItems.add(item)
        }
    }
    val stock = ArrayList<Item> ()
    while(stock.size<5 && stock.size<limitedItems.size){
        val rand = (0..limitedItems.size).random()
        val newItem = limitedItems[rand]
        stock.add(newItem)
        limitedItems.removeAt(rand)
    }

    while(finishedChoosing != "Yes"){
        for(item in stock){
            if(item.name != user.getArmor(true) && item.name != user.getCarry(true) && item.name != user.getRing(true)) {
                println("Item type " + item.type + " type '" + item.name + "' this item costs " + (1.5 * (item.priceBase + item.priceBase%2)) + " gold.")
            }
        }
        var input = readln()
        for(item in stock){
            if(input == item.name) {
                if((1.5 * (item.priceBase + item.priceBase%2))>user.gold){
                    println("You do not have enough gold for this item")
                }else {
                    itemExists = true
                    println(item.name + "\n" + item.description + "\nType 'Yes' to buy for " + (1.5 * (item.priceBase + item.priceBase%2)) + " gold.")
                    input = readln()
                    if (input == "Yes") {
                        user.gold-= (1.5 * (item.priceBase + item.priceBase%2)).toInt()
                        user.inventory.add(item)
                    }
                }
            }
        }
        if(!itemExists){
            println("Item does not exist, check your spelling")
        }
        println("Would you like to stop? Type 'Yes' to stop")
        finishedChoosing = readln()
    }
}

fun playerVPlayer(playerOne: Player, playerTwo: Player): String{
    var winner = ""
    playerOne.start()
    playerTwo.start()
    var p1Turn = playerOne.agility>playerTwo.agility
    if(playerOne.agility==playerTwo.agility){
        val random = (0..1).random()
        p1Turn = random == 0
    }
    if(p1Turn){
        println(playerOne.name + " went first!")
    }else{
        println(playerTwo.name + " went first!")
    }
    var turnOrder = 0
    var turn = -1
    while(winner == ""){
        if(turnOrder.mod(2)==0){
            turn++
            println("\nTURN " + (turn+1) + "\n")
            playerOne.updateTurn(playerTwo)
            playerTwo.updateTurn(playerOne)
        }
        if(p1Turn) {
            var sheathUsed = false
            if (playerOne.agility>0){
                println("Would you like to run away, Type 'Yes' to run a number of spaces away equal to or less than agility")
                var run = readln()
                if (run == "Yes") {
                    var invalidInput = true
                    while(invalidInput) {
                        println("You may run up to " + playerOne.agility + " spaces away, type the number in order to move that many spaces")
                        run = readln()
                        if (run == "1" || run == "2" || run == "3" || run == "4" || run == "5" || run == "6") {
                            val runNum = run.toInt()
                            println("Which way would you like to move, type 'Left' to go left and 'Right' to go right")
                            run = readln()
                            if(run=="Left") {
                                if(playerOne.position>playerTwo.position && playerOne.position - runNum < playerTwo.position){
                                    playerOne.position = playerTwo.position
                                    println("You can't run past an enemy without the enemy blocking you")
                                }else {
                                    playerOne.position -= runNum
                                }
                                invalidInput = false
                            }
                            if(run=="Right") {
                                if(playerOne.position<playerTwo.position && playerOne.position + run.toInt() > playerTwo.position){
                                    playerOne.position = playerTwo.position
                                    println("You can't run past an enemy without the enemy blocking you")
                                }else {
                                    playerOne.position += runNum
                                }
                                invalidInput = false
                            }
                        } else if(run=="0"){
                            println("Ok, you stand still, don't know why you needed to tell me you were going to move\n" +
                                    "You're not wasting my time, you're wasting your own")
                            invalidInput=false
                        }else{
                            println("Unfortunately what you inputted was incorrect, please try again")
                        }
                    }
                }
            }
            if(playerOne.alterChosen){
                println("Would you like to swap out your " + playerOne.getCarry(true) + " with a " + playerOne.getSheath())
            }else {
                println("Sheath current carry and swap to new weapon? Type 'Yes' to do so")
            }
            val swap = readln()
            if(swap=="Yes"){
                sheathUsed=true
                if(playerOne.alterChosen){
                    playerOne.swapWeapon()
                }else{
                    playerOne.sheathCarry()
                }
            }
            val skillUsed = playerOne.getSkills(playerTwo)
            if(!skillUsed && !sheathUsed) {
                val atk = playerOne.attacking(playerTwo)
                println(playerOne.name + " use " + playerOne.getCarry(false) + " to attack " + playerTwo.name + " dealing a potential " + atk.damage + " damage")
                playerTwo.attacked(playerOne, atk)
                if(playerOne.getSheath()!="none" && playerOne.getCarry(true)=="no weapon"){
                    playerOne.chooseWeapon()
                }
            }
        }else{
            var sheathUsed = false
            if (playerTwo.agility>0){
                println("Would you like to run away, Type 'Yes' to run a number of spaces away equal to or less than agility")
                var run = readln()
                if (run == "Yes") {
                    var invalidInput = true
                    while(invalidInput) {
                        println("You may run up to " + playerOne.agility + " spaces away, type the number in order to move that many spaces")
                        run = readln()
                        if (run == "1" || run == "2" || run == "3" || run == "4" || run == "5" || run == "6") {
                            val runNum = run.toInt()
                            println("Which way would you like to move, type 'Left' to go left and 'Right' to go right")
                            run = readln()
                            if(run=="Left") {
                                if(playerOne.position>playerTwo.position && playerOne.position - runNum < playerTwo.position){
                                    playerOne.position = playerTwo.position
                                    println("You can't run past an enemy without the enemy blocking you")
                                }else {
                                    playerOne.position -= runNum
                                }
                                invalidInput = false
                            }
                            if(run=="Right") {
                                if(playerOne.position<playerTwo.position && playerOne.position + run.toInt() > playerTwo.position){
                                    playerOne.position = playerTwo.position
                                    println("You can't run past an enemy without the enemy blocking you")
                                }else {
                                    playerOne.position += runNum
                                }
                                invalidInput = false
                            }
                        } else if(run=="0"){
                            println("Ok, you stand still, don't know why you needed to tell me you were going to move\n" +
                                    "You're not wasting my time, you're wasting your own")
                            invalidInput=false
                        }else{
                            println("Unfortunately what you inputted was incorrect, please try again")
                        }
                    }
                }
            }
            if(playerTwo.alterChosen){
                println("Would you like to swap out your " + playerTwo.getCarry(true) + " with a " + playerTwo.getSheath())
            }else {
                println("Sheath current carry and swap to new weapon? Type 'Yes' to do so")
            }
            val swap = readln()
            if(swap=="Yes"){
                sheathUsed=true
                if(playerTwo.alterChosen){
                    playerTwo.swapWeapon()
                }else{
                    playerTwo.sheathCarry()
                }
            }
            val skillUsed = playerTwo.getSkills(playerOne)
            if(!skillUsed && !sheathUsed) {
                val atk = playerTwo.attacking(playerOne)
                println(playerTwo.name + " use " + playerTwo.getCarry(false) + " to attack " + playerOne.name + " dealing a potential " + atk.damage + " damage")
                playerOne.attacked(playerTwo, atk)
                if(playerTwo.getSheath()!="none" && playerTwo.getCarry(true)=="no weapon"){
                    playerTwo.chooseWeapon()
                }
            }
        }
        if(!playerTwo.isAlive  && playerOne.isAlive){
            winner = playerOne.name
        }
        if(!playerOne.isAlive && playerTwo.isAlive){
            winner = playerTwo.name
        }
        if(!playerOne.isAlive && !playerTwo.isAlive){
            println("Draw")
            winner = "NONE"
        }
        p1Turn=!p1Turn
        turnOrder++
    }
    playerOne.end()
    playerTwo.end()
    return winner
}

fun playerVNPC(playerOne: Player, playerTwo: Player): String{
    var winner = ""
    playerOne.start()
    playerTwo.start()
    var p1Turn = playerOne.agility>=playerTwo.agility
    if(p1Turn){
        println(playerOne.name + " went first!")
    }else{
        println(playerTwo.name + " went first!")
    }
    var turnOrder = 0
    var turn = -1
    while(winner == ""){
        if(turnOrder.mod(2)==0){
            turn++
            println("\nTURN " + (turn+1) + "\n")
            playerOne.updateTurn(playerTwo)
            playerTwo.updateTurn(playerOne)
        }
        if(p1Turn) {
            var sheathUsed = false
            if (playerOne.agility>0){
                println("Would you like to run away, Type 'Yes' to run a number of spaces away equal to or less than agility")
                var run = readln()
                if (run == "Yes") {
                    var invalidInput = true
                    while(invalidInput) {
                        println("You may run up to " + playerOne.agility + " spaces away, type the number in order to move that many spaces")
                        run = readln()
                        if (run == "1" || run == "2" || run == "3" || run == "4" || run == "5" || run == "6") {
                            val runNum = run.toInt()
                            println("Which way would you like to move, type 'Left' to go left and 'Right' to go right")
                            run = readln()
                            if(run=="Left") {
                                if(playerOne.position>playerTwo.position && playerOne.position - runNum < playerTwo.position){
                                    playerOne.position = playerTwo.position
                                    println("You can't run past an enemy without the enemy blocking you")
                                }else {
                                    playerOne.position -= runNum
                                }
                                invalidInput = false
                            }
                            if(run=="Right") {
                                if(playerOne.position<playerTwo.position && playerOne.position + runNum > playerTwo.position){
                                    playerOne.position = playerTwo.position
                                    println("You can't run past an enemy without the enemy blocking you")
                                }else {
                                    playerOne.position += runNum
                                }
                                invalidInput = false
                            }
                        } else if(run=="0"){
                            println("Ok, you stand still, don't know why you needed to tell me you were going to move\n" +
                                    "You're not wasting my time, you're wasting your own")
                            invalidInput=false
                        }else{
                            println("Unfortunately what you inputted was incorrect, please try again")
                        }
                    }
                }
            }
            if(abs(playerOne.position-playerTwo.position)>=15){
                println("You are almost away from the enemy, would you like to run away? Type 'Yes' to do so")
                val run = readln()
                if(run=="Yes"){
                    winner = "none"
                }
            }
            if(playerOne.alterChosen){
                println("Would you like to swap out your " + playerOne.getCarry(true) + " with a " + playerOne.getSheath())
            }else {
                println("Sheath current carry and swap to new weapon? Type 'Yes' to do so")
            }
            val swap = readln()
            if(swap=="Yes"){
                sheathUsed=true
                if(playerOne.alterChosen){
                    playerOne.swapWeapon()
                }else{
                    playerOne.sheathCarry()
                }
            }
            var skillUsed = false
            if(!sheathUsed) {
                skillUsed = playerOne.getSkills(playerTwo)
            }
            if(!skillUsed && !sheathUsed) {
                val atk = playerOne.attacking(playerTwo)
                println(playerOne.name + " use " + playerOne.getCarry(false) + " to attack " + playerTwo.name + " dealing a potential " + atk.damage + " damage")
                playerTwo.attacked(playerOne, atk)
                if(playerOne.getSheath()!="none" && playerOne.getCarry(true)=="no weapon"){
                    playerOne.chooseWeapon()
                }
            }
        }else{
            if(playerTwo.position!=playerOne.position && playerTwo.getCarry(true) != "Bow"){
                if(playerOne.position>playerTwo.position){
                   if(playerTwo.position + playerTwo.agility>playerOne.position){
                       playerTwo.position=playerOne.position
                       println("Enemy moved to your position")
                   }else{
                       playerTwo.position+=playerTwo.agility
                       println("Enemy moved " + playerTwo.agility + " spaces right")
                   }
                }else{
                    if(playerTwo.position - playerTwo.agility<playerOne.position){
                        playerTwo.position=playerOne.position
                        println("Enemy moved to your position")
                    }else{
                        playerTwo.position-=playerTwo.agility
                        println("Enemy moved " + playerTwo.agility + " spaces left")
                    }
                }
            } else if (playerTwo.getCarry(true) == "Bow"){
                if ((playerOne.position > playerTwo.position && abs(playerTwo.position - playerTwo.agility) <= 10) || (abs(playerTwo.position - playerTwo.agility) > 10 && playerOne.position < playerTwo.position)) {
                    playerTwo.position -= playerTwo.agility
                    if()
                    println("Enemy moved " + playerTwo.agility + " spaces left")
                } else if ((playerOne.position < playerTwo.position && abs(playerTwo.position - playerTwo.agility) <= 10) || (abs(playerTwo.position - playerTwo.agility) > 10 && playerOne.position > playerTwo.position)){
                    playerTwo.position += playerTwo.agility
                    println("Enemy moved " + playerTwo.agility + " spaces right")
                }
            }
            val atk = playerTwo.attacking(playerOne)
            println(playerTwo.name + " use " + playerTwo.getCarry(false) + " to attack " + playerOne.name + " dealing a potential " + atk.damage + " damage")
            playerOne.attacked(playerTwo,atk)
        }
        if(!playerTwo.isAlive){
            winner = playerOne.name
        }
        if(!playerOne.isAlive && playerTwo.isAlive){
            winner = playerTwo.name
        }
        p1Turn=!p1Turn
        turnOrder++
    }
    playerOne.end()
    playerTwo.end()
    return winner
}

fun manyVMany(players: ArrayList<Player>, pvp: Boolean, freeForAl: Boolean): String{
    var winner = ""
    for(player in players){
        player.start()
    }
    val order = ArrayList<Player>()
    for(player in players){
        if(order.size>0){
            var i = 0
            var unadded = true
            while(i<order.size){
                if(player.agility>=order[i].agility){
                    order.add(i,player)
                    unadded = false
                }
                i++
            }
            if(unadded){
                order.add(player)
            }
        }else{
            order.add(player)
        }
    }
    var turn = -1
    while(winner == ""){
        turn++
        println("\nTURN " + (turn+1) + "\n")
        for(player in players) {
            player.updateTurn(players)
        }
        for(player in players){
            println(player.name + " s' turn")
            if(player.isReal){
                var sheathUsed = false
               if (player.agility>0){
                    println("Would you like to run away, Type 'Yes' to run a number of spaces away equal to or less than agility")
                    var run = readln()
                    if (run == "Yes") {
                        var invalidInput = true
                        while(invalidInput) {
                            println("You may run up to " + player.agility + " spaces away, type the number in order to move that many spaces")
                            run = readln()
                            if (run == "1" || run == "2" || run == "3" || run == "4" || run == "5" || run == "6") {
                                val runNum = run.toInt()
                                println("Which way would you like to move, type 'Left' to go left and 'Right' to go right")
                                run = readln()
                                if(run=="Left") {
                                    /*if(player.position>playerTwo.position && playerOne.position - runNum < playerTwo.position){
                                        playerOne.position = playerTwo.position
                                        println("You can't run past an enemy without the enemy blocking you")
                                    }else {*/
                                        player.position -= runNum
                                   // }
                                    invalidInput = false
                                }
                                if(run=="Right") {
                                    /*if(player.position<playerTwo.position && playerOne.position + run.toInt() > playerTwo.position){
                                        player.position = playerTwo.position
                                        println("You can't run past an enemy without the enemy blocking you")
                                    }else {*/
                                        player.position += runNum
                                    //}
                                    invalidInput = false
                                }
                            } else if(run=="0"){
                                println("Ok, you stand still, don't know why you needed to tell me you were going to move\n" +
                                        "You're not wasting my time, you're wasting your own")
                                invalidInput=false
                            }else{
                                println("Unfortunately what you inputted was incorrect, please try again")
                            }
                        }
                    }
                }
                if(player.alterChosen){
                    println("Would you like to swap out your " + player.getCarry(true) + " with a " + player.getSheath())
                }else {
                    println("Sheath current carry and swap to new weapon? Type 'Yes' to do so")
                }
                val swap = readln()
                if(swap=="Yes"){
                    sheathUsed=true
                    if(player.alterChosen){
                        player.swapWeapon()
                    }else{
                        player.sheathCarry()
                    }
                }
                val playerTarget = chooseTarget(player, players, pvp)

                val skillUsed = player.getSkills(playerTarget)
                if(!skillUsed && !sheathUsed) {
                    val atk = player.attacking(playerTarget)
                    println(player.name + " use " + player.getCarry(false) + " to attack " + playerTarget.name + " dealing a potential " + atk.damage + " damage")
                    player.attacked(player, atk)
                    if(player.getSheath()!="none" && player.getCarry(true)=="no weapon"){
                        player.chooseWeapon()
                    }
                }
            }else{
                var target = Player(false,"Null")
                for(other in players){
                    val endure = player.endurance
                    if(abs(other.position-player.position)<=player.attacking(target).range + player.agility && player.isAlive && other.isAlive && (other.isReal||freeForAl)){
                        if(endure!=other.endurance){
                            other.endurance=endure
                        }
                        target = other
                    }
                }
                if(target.name!="Null") {
                    if (player.position != target.position && player.getCarry(true) != "Bow") {
                        if (target.position > player.position) {
                            if (player.position + player.agility > target.position) {
                                player.position = target.position
                            } else {
                                player.position += player.agility
                            }
                        } else {
                            if (player.position - player.agility < target.position) {
                                player.position = target.position
                            } else {
                                player.position -= target.agility
                            }
                        }
                    } else if (player.getCarry(true) == "Bow") {
                        if (target.position > player.position) {
                            player.position -= player.agility
                        } else {
                            player.position += target.agility
                        }
                    }
                    val atk = player.attacking(target)
                    println(player.name + " use " + player.getCarry(false) + " to attack " + target.name + " dealing a potential " + atk.damage + " damage")
                    target.attacked(player, atk)
                }
            }
        }
        var numPlayersAlive = 0
        var numOfRealPlayers = 0
        for(player in players){
            if(player.isAlive){
                numPlayersAlive++
                if(player.isReal){
                    numOfRealPlayers++
                }
            }
        }
        if(numOfRealPlayers == 0 && !freeForAl){
            winner = "Enemies"
        }
        if(numOfRealPlayers == 1 && pvp){
            for(player in players){
                if(player.isAlive){
                    winner = player.name
                }
            }
        }
        if(numPlayersAlive == numOfRealPlayers){
            winner = "Players"
        }
        if(numPlayersAlive==0){
            winner = "none"
        }
    }
    for(player in players){
        player.start()
    }
    return winner
}

fun chooseCreature(userPlayer: Player, creatures: ArrayList<Creature>){
    var creatureChosen = false
    var creatureExists = false
    while(!creatureChosen){
        for(creature in creatures){
            println("Type '" + creature.name + "' to play as a " + creature.description)
        }
        val name = readln()
        for(creature in creatures){
            if(name == creature.name){
                println("Creature Stats: " + creature.toString() + creature.powerDescription())
                userPlayer.newCreature(creature)
                creatureExists = true
            }
        }
        if(creatureExists) {
            println("Is this the creature you want? Type 'Yes' to accept")
            val choose = readln()
            if (choose=="Yes") {
                creatureChosen = true
            }
        }else{
            println("Creature does not exist, check your spelling")
        }
    }
}

fun chooseTarget(userPlayer: Player, players: ArrayList<Player>, pvp: Boolean) : Player{
    var playerExists: Boolean
    var chosenPlayer = Player(false, "null")
    while(chosenPlayer.name!="null"){
        playerExists=false
        for(player in players){
            var valid = true
            if(!pvp){
                valid = !player.isReal
            }
            if(player.name!=userPlayer.name && valid) {
                println("Type '" + player.name + "' to attack " + player.getDescription(false))
            }
        }
        val name = readln()
        for(player in players){
            if(name == player.name){
                println(player.getDescription(false))
                chosenPlayer=player
                playerExists=true
            }
        }
        if(playerExists) {
            println("Is this the creature you want? Type 'Yes' to accept")
            val choose = readln()
            if (choose!="Yes") {
                chosenPlayer = Player(false, "null")
            }else if(chosenPlayer.name == userPlayer.name){
                println("Play stupid games, win stupid prizes")
            }
        }else{
            println("Creature does not exist, check your spelling")
        }
    }
    return chosenPlayer
}

class Player(val isReal: Boolean, val name: String){
    var myCreature = Creature("None", 0, 0, 0, 0, 0, 0, "None")
    private val items = ArrayList<Item>()
    var position = 0
    var turn = -1
    private var swapped = false
    var alterChosen = false
    //stats
    var skill = myCreature.skill
    var agility = myCreature.agility
    var health = myCreature.maxHealth
    var endurance = myCreature.maxEndurance
    var mana = myCreature.maxMana
    var isAlive = true
    var gold = 0

    var inventory = ArrayList<Item>()

    fun getGearPrice() : Int{
        var price = 0
        for(item in items){
            price += item.priceBase
        }
        return price
    }

    fun chooseWeapon(){
        var finishedChoosing = "NO"
        var itemExists = false
        while(finishedChoosing != "Yes"){
            for(item in inventory){
                if(item.type=="Carry" && getSheath()!=item.name) {
                    println("Type '" + item.name + "' to add this to make this item your weapon")
                }
            }
            var input = readln()
            for(item in inventory){
                if(input == item.name && item.type == "Carry" && getSheath()!=item.name) {
                    itemExists=true
                    println(item.name + "\n" + item.description + "\nType 'Yes' to add")
                    input = readln()
                    if (input == "Yes"){
                        if (this.addItem(item)) {
                            println(item.name + " has been added!")
                        } else {
                            println(item.name + " cannot be added as you cannot have two of " + item.type + " at once")
                            println("Would you like to replace " + getCarry(true) + " with " + item.name + ", type 'Yes' to do so")
                            input = readln()
                            if (input == "Yes") {
                                replaceItem(item)
                            }
                        }
                    }
                }
            }
            if(!itemExists){
                println("Item does not exist, check your spelling")
            }
            println("Would you like to stop? Type 'Yes' to stop")
            finishedChoosing = readln()
        }
    }

    fun chooseItem(){
        var finishedChoosing = "NO"
        var itemExists = false
        while(finishedChoosing != "Yes"){
            for(item in inventory){
                if(item.name != getArmor(true) && item.name != getCarry(true) && item.name != getRing(true)) {
                    println("Item type " + item.type + " type '" + item.name + "' to add this item to inventory")
                }
            }
            var input = readln()
            for(item in inventory){
                if(input == item.name) {
                    itemExists=true
                    println(item.name + "\n" + item.description + "\nType 'Yes' to add")
                    input = readln()
                    if (input == "Yes"){
                        if (this.addItem(item)) {
                            println(item.name + " has been added!")
                        } else {
                            println(item.name + " cannot be added as you cannot have two of " + item.type + " at once")
                            when (item.type) {
                                "Carry" -> {
                                    println("Would you like to replace " + getCarry(true) + " with " + item.name + ", type 'Yes' to do so")
                                }
                                "Armor" -> {
                                    println("Would you like to replace " + getArmor(true) + " with " + item.name + ", type 'Yes' to do so")
                                }
                                "Ring" -> {
                                    println("Would you like to replace " + getRing(true) + " with " + item.name + ", type 'Yes' to do so")
                                }
                            }
                            input = readln()
                            if (input == "Yes") {
                                replaceItem(item)
                            }
                        }
                    }
                }
            }
            if(!itemExists){
                println("Item does not exist, check your spelling")
            }
            println("Would you like to stop? Type 'Yes' to stop")
            finishedChoosing = readln()
        }
    }

    fun sheathCarry(){
        for(item in items){
            if(item.type == "Carry"){
                item.type="Sheathed"
            }
        }
        alterChosen=true
        chooseWeapon()
    }

    fun getRange() : Int{
        val player = Player(false, "NULL")
        return attacking(player).range
    }

    fun getDescription(trueSight: Boolean):String{
        return "A " + getCreature(trueSight) + " wearing " + getArmor(trueSight) + " holding " + getCarry(trueSight)
    }

    fun getSkills(target: Player): Boolean{
        var skillUsed = false
        var isSkills = false
        var skillsAvailable = 0
        for(item in items){
            if(item.skillOneIsActive(this) != "NULL"){
                isSkills = true
                skillsAvailable++
            }
            if(item.skillTwoIsActive(this) != "NULL"){
                isSkills = true
                skillsAvailable++
            }
        }
        if(isSkills){
            var selectSkills = "Yes"
            while(selectSkills == "Yes" && !skillUsed) {
                for (item in items) {
                    if (item.skillOneIsActive(this) != "NULL") {
                        println(item.skillOneIsActive(this))
                    }
                }
                for (item in items) {
                    if (item.skillTwoIsActive(this) != "NULL") {
                        println(item.skillTwoIsActive(this))
                    }
                }
                println("You have $skillsAvailable skills avaliable, Type the name to use.")
                var input = readln()
                var skillExists = false
                for (item in items) {
                    if (item.skillOneIsActive(this) != "NULL" && item.skillOneName() == input && !skillUsed) {
                        skillExists=true
                        println(item.skillOneIsActive(this))
                        println("Would you like to use "+ item.skillOneName() + "? Type 'Yes' to do so")
                        input = readln()
                        if(input == "Yes"){
                            item.skillOne(this,target)
                            skillUsed = true
                        }
                    }

                    if (item.skillTwoIsActive(this) != "NULL" && item.skillTwoName() == input && !skillUsed) {
                        skillExists=true
                        println(item.skillTwoIsActive(this))
                        println("Would you like to use "+ item.skillTwoName() + "? Type 'Yes' to do so")
                        input = readln()
                        if(input == "Yes"){
                            item.skillTwo(this,target)
                            skillUsed = true
                        }
                    }
                }
                if(!skillExists){
                    println("That skill either does not exist, is currently inaccessible at your skill level, or exists on a different item")
                }
                if(!skillUsed){
                    println("Would you like to try and use a different skill? Type 'Yes' to do so")
                    selectSkills = readln()
                }
            }
        }
        return skillUsed
    }

    fun getCreature(trueSight: Boolean): String{
        for(item in items){
            if(item.name == "Cloak" && !trueSight){
                return "Cloaked Figure"
            }
        }
        return myCreature.name
    }

    fun getSheath() : String{
        for(item in items){
            if(item.type == "Sheathed"){
                return item.name
            }
        }
        return "none"
    }

    fun swapWeapon(){
        swapped = !swapped
        var ogItem = Item("null","null","null", 0)
        for(item in items){
            if(item.type == "Carry"){
                item.type="Sheathed"
                ogItem=item
            }
        }
        for(item in items){
            if(item.type == "Sheathed" && item.name != ogItem.name){
                item.type="Carry"
            }
        }
    }

    fun getCarry(trueSight: Boolean): String{
        var itemName = "no weapon"
        for(item in items){
            if(item.type == "Carry"){
                itemName = item.getItemName(trueSight)
            }
            if(item.name == "Cloak"&& !trueSight){
                return "Something that can't be seen"
            }
        }
        return itemName
    }

    fun getArmor(trueSight: Boolean): String{
        var itemName = "no armor"
        for(item in items){
            if(item.type == "Armor"){
                itemName = item.getItemName(trueSight)
            }
        }
        return itemName
    }

    fun getRing(trueSight: Boolean): String{
        var itemName = "no ring"
        if(getArmor(trueSight)=="Cloak" && !trueSight){
            return "ring that can't be seen"
        }
        for(item in items){
            if(item.type == "Ring"){
                itemName = item.name
            }
        }
        return itemName
    }

    fun updateTurn(other: Player){
        turn++
        myCreature.turn++
        myCreature.onStartOfTurn(this)
        for(item in items){
            item.onStartOfTurn(this, other)
        }
    }

    fun updateTurn(players: ArrayList<Player>){
        turn++
        myCreature.turn++
        myCreature.onStartOfTurn(this)
        for(item in items){
            item.onStartOfTurn(this, players)
        }
    }

    fun addItem(newItem: Item): Boolean{
        for(item in items){
            if(item.type.equals(newItem.type)){
                return false
            }
        }
        items.add(newItem)
        return true
    }

    private fun replaceItem(newItem: Item){
        var i = 0
        while(i<items.size){
            if(items[i].type == newItem.type){
                break
            }
            i++
        }
        items[i] = newItem
    }

    fun newCreature(c: Creature){
        myCreature = c
    }

    fun start(){
        turn = -1
        myCreature.turn=-1
        skill = myCreature.skill
        agility = myCreature.agility
        health = myCreature.maxHealth
        endurance = myCreature.maxEndurance
        mana = myCreature.maxMana
        isAlive=true
        swapped=false
        alterChosen=false
        for(item in items){
            item.updateStats(this)
        }
    }

    fun end(){
        if(swapped){
            swapWeapon()
        }
        for(item in items){
            item.unUpdateStats(this)
            if(item.type=="Carry" && getSheath() != "none"){
                items.remove(item)
            }
        }
        if(getSheath() != "none"){
            for(item in items){
                if(item.type=="Sheathed"){
                    item.type="Carry"
                }
            }
        }
    }

    private fun getIsSummoned(): Boolean{
        for (item in items) {
            if(item.isSummoned()){
                return true
            }
        }
        return false
    }

    fun attacked(attacker: Player, atk: Attack) {
        val isSummon = getIsSummoned()
        if (abs(attacker.position - position) > atk.range && !isSummon) {
            println("Attack not within range")
        } else{
            var hit = atk
            hit = myCreature.onHit(this, attacker, hit)
            for (item in items) {
                if(!isSummon || item.isSummoned()) {
                    hit = item.onHit(this, attacker, hit)
                }
            }
            hit(attacker, hit)
        }
    }

    fun attacking(target: Player): Attack{
        var atk = Attack(0, 0, dodgeable = true, magical = false)
        atk = myCreature.onAttack(this, target, atk)
        for(item in items){
            atk = item.onAttack(this, target, atk)
        }
        return atk
    }

    private fun hit(attacker : Player, atk: Attack){

        var dN = atk.damage
        if(endurance>0 && atk.dodgeable && atk.damage>0){
            var input = ""
            if(isReal) {
                println("Absorb damage with endurance? Type 'NO' if you don't want to")
                input = readln()
            }
            if(input != "NO"){
                if(dN>endurance){
                    dN -= endurance
                    println("$name almost dodged the attack, still taking $dN damage")
                    spendEndurance(endurance, "dodge")
                }else{
                    println("$name dodged the attack")
                    spendEndurance(dN, "dodge")
                    dN = 0
                }
            }
        }
        if(dN>0) {
            if(atk.magical) {
                takeDamage(attacker, dN, "spell")
            }else {
                takeDamage(attacker, dN, "attack")
            }
        }
    }

    fun takeDamage(attacker: Player, d: Int, reason: String){
        var damage = d
        damage = myCreature.onTakeDamage(this, attacker, damage, reason)
        for(item in items){
            damage = item.onTakeDamage(this, attacker, damage, reason)
        }
        println("$name takes $damage damage from $reason")
        health-=d
        checkHealth()
        if(isReal && isAlive){
            println("You now have $health health left")
        }
    }

    fun spendEndurance(n: Int, reason: String){
        var enduranceSpent = n
        enduranceSpent = myCreature.onSpendEndurance(this, enduranceSpent, reason)
        for(item in items){
            enduranceSpent = item.onSpendEndurance(this, enduranceSpent, reason)
        }
        if(endurance-n>=0){
            endurance-=n
            println("$name spent $enduranceSpent endurance")
            if(isReal){
                println("You now have $endurance endurance left")
            }
        }
    }

    fun spendMana(m: Int, reason: String){
        var manaSpent = m
        manaSpent = myCreature.onSpendMana(this, manaSpent, reason)
        for(item in items){
            manaSpent = item.onSpendMana(this, manaSpent, reason)
        }
        if(mana-manaSpent>0){
            mana-=manaSpent
            println("$name spent $manaSpent mana")
            if(isReal){
                println("You now have $mana mana left")
            }
        }
    }

    private fun checkHealth(){
        if(health<=0){
            isAlive = false
            println("$name is knocked out!")
        }
    }
}

open class Creature(val name: String, val maxHealth: Int, val damage: Int, val maxEndurance: Int, val agility: Int, val maxMana: Int, val skill: Int, val description: String){

    var turn = 0

    open fun powerDescription():String{
        return ""
    }
    open fun onAttack(user: Player, target: Player, atk: Attack): Attack {
        atk.damage += damage
        return atk
    }
    open fun onHit(user: Player, target: Player, atk: Attack): Attack {
        return atk
    }
    open fun onSpendEndurance(user: Player, enduranceSpent: Int, reason: String): Int{
        return enduranceSpent
    }
    open fun onSpendMana(user: Player, manaSpent: Int, reason: String): Int{
        return manaSpent
    }
    open fun onTakeDamage(user: Player, target: Player, damage: Int, reason: String): Int{
        return damage
    }
    open fun onStartOfTurn(person: Player){}
    override fun toString(): String{
        return "$maxHealth Health, $damage Base Damage, $maxEndurance Endurance, $agility Agility, $maxMana Mana, $skill Skill"
    }
}

class Monk : Creature("Monk",4,0,4,4,4,3,"A Spiritualist who is skilled in magics"){
    override fun powerDescription(): String {
        return "\nKI: Spend mana instead of endurance when using a endurance based ability."
    }
    override fun onSpendEndurance(user: Player, enduranceSpent: Int, reason: String): Int {
        if(user.mana>=enduranceSpent && enduranceSpent>0 && user.isReal){
            println("Spend mana instead of endurance for $reason? Type 'Yes' to do so!")
            val choice = readln()
            if(choice=="Yes"){
                user.spendMana(enduranceSpent,reason)
                println("$enduranceSpent mana has been spent. " + user.mana + " mana left")
                return 0
            }
        }
        return enduranceSpent
    }
}

class Mage : Creature("Mage", 3, 0, 4, 2, 5, 3, "A magic user who uses potent spells"){
    override fun powerDescription(): String {
        return "\nMAGICAL PROWESS: Gain 1 mana at the start of each round."
    }

    override fun onStartOfTurn(person: Player) {
        if(person.mana<person.myCreature.maxMana){
            person.mana+=1
            println(person.name + " regained 1 mana")
        }else if(person.turn!=1){
            println(person.name + " mana is already max")
        }
    }
}

class Rogue : Creature("Rogue", 3, 0,5, 5, 4, 2, "a sneaky and clever human who deals damage through dirty tricks"){
    override fun powerDescription(): String {
        return "\nSNEAK ATTACK: When dealing damage on first turn increases damage by 2"
    }
    override fun onAttack(user: Player, target: Player, atk: Attack): Attack {
        atk.damage += damage
        if (turn == 0) {
            atk.damage += 2
        }
        return atk
    }
}

class Skeleton: Creature("Skeleton", 3, 0, 3, 2, 4, 1, "an undead creature of pure bones"){
    override fun powerDescription(): String {
        return "\nUNDEAD ENDURANCE: If an endurance based ability decreases endurance to 0 then add 3 endurance"
    }
    override fun onSpendEndurance(user: Player, enduranceSpent: Int, reason: String): Int {
        if(user.endurance-enduranceSpent==0){
            user.endurance += 3
        }
        return super.onSpendEndurance(user, enduranceSpent, reason)
    }
}

class Goblin: Creature("Goblin", 3, 0, 3, 4, 3, 3, "a small yet crafty fellow who follow no rules but their own"){
    override fun powerDescription(): String {
        return "\nCRAFTY: Mana or endurance costing abilities use 1 less mana or endurance"
    }

    override fun onSpendEndurance(user: Player, enduranceSpent: Int, reason: String): Int{
        if(enduranceSpent>0) {
            return enduranceSpent - 1
        }
        return 0
    }

    override fun onSpendMana(user: Player, manaSpent: Int, reason: String): Int {
        if(manaSpent>0) {
            return manaSpent - 1
        }
        return 0
    }
}

class Troll: Creature("Troll", 5, 1, 2, 1, 1, 0, "a strong yet mindless brute with skin as tough as steel"){
    override fun powerDescription(): String {
        return "\nTHICK SKIN: When an attack lands directly, damage is decreased by 1"
    }
    override fun onHit(user: Player, target: Player, atk: Attack) : Attack{
        if(atk.damage==1 && !atk.magical){
            atk.damage=0
            println("The attack bounced harmlessly off the creature")
        }
        return atk
    }
    override fun onTakeDamage(user: Player, target: Player, damage: Int, reason: String): Int {
        if(damage>0 && reason!="SPELL") {
            println("The creatures thick skin protects them from 1 direct damage, instead dealing " + (damage-1) + " damage")
            return damage - 1
        }
        return 0
    }
}

open class Item(val name: String, var type: String, val description: String, var priceBase: Int){
    open fun isSummoned(): Boolean{
        return false
    }
    open fun getItemName(trueSight: Boolean): String{
        return name
    }
    open fun skillOneIsActive(user: Player): String{
        return "NULL"
    }
    open fun skillOneName():String{
        return "NULL"
    }
    open fun skillOne(user: Player, target: Player){
    }
    open fun skillTwoIsActive(user: Player): String{
        return "NULL"
    }
    open fun skillTwoName():String{
        return "NULL"
    }
    open fun skillTwo(user: Player, target: Player){
    }
    open fun onAttack(user: Player, target: Player, atk: Attack): Attack {
        return atk
    }
    open fun onHit(user: Player, target: Player, atk: Attack): Attack {
        return atk
    }
    open fun onSpendEndurance(user: Player, enduranceSpent: Int, reason: String): Int{
        return enduranceSpent
    }
    open fun onSpendMana(user: Player, manaSpent: Int, reason: String): Int{
        return manaSpent
    }
    open fun onTakeDamage(user: Player, target: Player, damage: Int, reason: String): Int{
        return damage
    }
    open fun updateStats(person: Player){}
    open fun unUpdateStats(person: Player){}
    open fun onStartOfTurn(user: Player, target: Player){}
    open fun onStartOfTurn(user: Player, targets: ArrayList<Player>){}
}

class Sword : Item("Sword", "Carry", "The classic weapon of any great hero, standard but proven effective.\n" +
        "1 Skill, 1 Endurance: Parry, decrease damage taken by 1\n" +
        "2 Skill, 2 Endurance: True Hit, attack can't be dodged", 3){

    override fun onAttack(user: Player, target: Player, atk: Attack): Attack {

        atk.damage++
        if (user.endurance > 1 && user.skill > 1 && atk.dodgeable && atk.range == 0 && user.isReal) {

            println("Spend 2 endurance to make the attack undodgeable. Type 'Yes' to do so")
            val input = readln()
            if (input == "Yes") {
                user.position=target.position
                atk.dodgeable = false
                user.spendEndurance(2, "skill")
            }
        }
        return atk
    }
    override fun onHit(user: Player, target: Player, atk: Attack): Attack {

        if (atk.damage > 0 && user.endurance > 0 && user.skill > 0 && !atk.magical && atk.range == 0 && user.isReal) {

            println("Would you like to parry this attack? Decreasing the damage of the attack by 1 at the cost of 1 endurance. Type 'Yes' to do so")
            val input = readln()
            if (input == "Yes") {
                atk.damage--
                user.spendEndurance(1, "skill")

            }
        }
        return atk
    }
}

class Spear : Item("Spear", "Carry", "One of the most simple yet effective weapons, distance is usually better.\n" +
        "1 Skill, 0 Endurance, Throw: throw your weapon at the opponent making an attack with range, instantly sheathing it in favor of an item of your choice\n" +
        "2 Skill, 0 Endurance, Defensive Reach: Damage taken from non reach weapons is decreased by 1", 3){

    override fun onAttack(user: Player, target: Player, atk: Attack): Attack {
        if(type=="Carry"){
            atk.damage++
        }
        return atk
    }
    override fun onHit(user: Player, target: Player, atk: Attack): Attack {

        if (user.skill > 1 && !atk.magical && atk.range < 1) {
            atk.damage--
            println("The spear protects " + user.name + " from one damage! Dealing " + atk.damage + " damage.")
        }
        return atk
    }

    override fun skillOneIsActive(user: Player): String{
        if(user.skill>0 && user.getSheath() == "none") {
            return "Spear throw: 1 Skill, throws your spear"
        }
        return "NULL"
    }
    override fun skillOneName(): String{
        return "Spear throw"
    }
    override fun skillOne(user: Player, target: Player){
        println("A Spear is thrown at " + target.name)
        val atk = user.attacking(target)
        atk.range += 10
        atk.damage++
        type="Sheathed"
        target.attacked(user, atk)
        println("Your spear was thrown! Choose a new weapon")
        user.chooseWeapon()
    }
}

class Bow : Item("Bow", "Carry", "A simple ranged weapon, attacks deal 1 damage. -1 damage when attacking at close range\n" +
        "1 Skill, 0 Endurance, Close Ranger: Attacks at close range don't deal -1 damage\n" +
        "2 Skill, 2 Endurance, Wonder Shot: Attack becomes undodgeable", 3){

    override fun onAttack(user: Player, target: Player, atk: Attack): Attack {
        atk.damage++
        atk.range += 10
        if (user.skill < 1 && user.position - target.position == 0) {
            atk.damage--
        }
        if (user.skill > 1 && user.endurance > 1 && atk.dodgeable && user.isReal) {
            println("Activate Wonder Shot, making the attack undodgeable? Type 'Yes' to do so")
            val input = readln()
            if (input == "Yes") {
                atk.dodgeable = false
                atk.range+=10
            }
        }
        return atk
    }
}

class GreatAxe : Item("Great Axe", "Carry", "A Heavy Weapon, attacks deal 2 damage but costs 1 endurance per attack.\n" +
        "1 Skill, 2 Endurance, Exhausting attack: The opponent loses 1 endurance along with the attack\n" +
        "2 Skill, 0 Endurance, Great Endurance: Attack does not cost endurance to use", 5){

    override fun onAttack(user: Player, target: Player, atk: Attack): Attack {

        if (user.endurance > 0 || user.skill > 0) {
            atk.damage += 2
            if(user.skill>1){
                target.spendEndurance(1,"dodge")
                println("The weapons heavy strike exhausts " + target.name + " to dodge costing 1 endurance!")
                if(user.isReal){
                    println("You now have " + user.endurance + " left")
                }
            }
            if (user.skill < 2) {
                user.spendEndurance(1, "dodge")
                println("The heft of the weapon costs " + user.name + " one endurance to weild!")
                if(user.isReal){
                    println("You now have " + user.endurance + " left")
                }
            }
        }else if(user.endurance==0){
            println("The enemy is tired while weilding this weapon, and attacks without it!")
        }
        return atk
    }
}

class HeavyArmor : Item("Heavy Armor", "Armor", "Heavy weighted armor, all non-magical damage taken is decreased by 1 but dodging costs +1 endurance, wearer is slowed", 5){
    override fun onHit(user: Player, target: Player, atk: Attack): Attack {
        if (!atk.magical && atk.damage > 0) {
            atk.damage--
            println("The nature of the armor protects " + user.name + " from one damage! Dealing " + atk.damage + " damage.")
        }
        return atk
    }

    override fun updateStats(person: Player) {
        person.agility--
    }

    override fun unUpdateStats(person: Player) {
        person.agility++
    }

    override fun onSpendEndurance(user: Player, enduranceSpent: Int, reason: String): Int {
        if(reason=="dodge") {
            return enduranceSpent + 1
        }
        return enduranceSpent
    }
}

class LightArmor: Item("Light Armor", "Armor", "Light weight armor, all magical damage is decreased by 1 against the armor, every other turn endurance increases by 1 to a max of the creatures", 2){
    override fun onHit(user: Player, target: Player, atk: Attack): Attack {
        if (atk.magical && atk.damage > 0) {
            atk.damage--
            println("The nature of the armor protects " + user.name + " from one damage! Dealing " + atk.damage + " damage.")
        }
        return atk
    }

    override fun onStartOfTurn(user: Player, target: Player) {
        if(user.turn.mod(2)==1 && user.endurance<user.myCreature.maxEndurance){
            user.endurance++
            println(user.name + " endurance increases by 1")
        }else if(user.endurance<user.myCreature.maxEndurance){
            println(user.name + " endurance is already max")
        }
    }
}

class MartialArmor : Item("Martial Armor", "Armor", "Someone who uses martial arts as their armor, regain 1 endurance at the start of each round and when taking damage",1){
    override fun onStartOfTurn(user: Player, target: Player) {
        if(user.endurance<user.myCreature.maxEndurance){
            user.endurance++
            println(user.name + " regained endurance")
        }else if(user.turn!=0){
            println(user.name + " endurance is already max")
        }
    }

    override fun onTakeDamage(user: Player, target: Player, damage: Int, reason: String): Int {
        if(user.endurance<user.myCreature.maxEndurance){
            user.endurance++
            println(user.name + " was hit and regained endurance")
        }else if(user.turn!=1){
            println(user.name + " was hit but endurance is already max")
        }
        return damage
    }
    override fun getItemName(trueSight: Boolean): String {
        if(trueSight){
            return "Martial Armor"
        }
        return "no armor"
    }
}

class MageRobes : Item("Mage Robes","Armor","Robes of a mage, regain 1 mana at the start of each round\n" +
        "MANA SHIELD: Decrease damage taken using mana, -1 cost for magical attacks, +1 for non magical", 5){
    override fun onStartOfTurn(user: Player, target: Player) {
        if(user.mana<user.myCreature.maxMana){
            user.mana+=1
            println(user.name + " regained 1 mana")
        }else if(user.turn!=0){
            println(user.name + " mana is already max")
        }
    }

    override fun onHit(user: Player, target: Player, atk: Attack): Attack {
        if (atk.magical && atk.damage > 0 && user.isReal) {
            println("Absorb damage with mana? Press N for no")
            val input = readln()
            if (input == "N") {
                if (atk.damage > user.mana + 1) {
                    atk.damage -= user.mana
                    user.spendMana(user.mana - 1, "mana shield")
                } else {
                    user.spendMana(atk.damage - 1, "mana shield")
                    atk.damage = 0
                }
            }
        }else if(atk.damage > 0 && user.isReal && user.mana>1){
            println("Absorb damage with mana? Press N for no")
            val input = readln()
            if (input == "N") {
                if (atk.damage > user.mana - 1) {
                    atk.damage -= user.mana
                    user.spendMana(user.mana + 1, "mana shield")
                } else {
                    user.spendMana(atk.damage + 1, "mana shield")
                    atk.damage = 0
                }
            }
        }
        return atk
    }
}

class Cloak : Item("Cloak","Armor","A mysterious hood that hides the features of the wearer\n" +
        "Flip two coins, each coin that is tails regain one mana, each coin that is heads regain one endurance", 1){
    override fun onStartOfTurn(user: Player, target: Player) {
        if (user.turn != 0) {
            repeat(2) {
                val random = (0..1).random()

                if (random == 0 && user.mana < user.myCreature.maxMana) {
                    user.mana++
                    println(user.name + " regained mana")
                } else if (random == 0 && user.mana >= user.myCreature.maxMana && user.turn != 1) {
                    println(user.name + " mana is already max")
                }
                if (random == 1 && user.endurance < user.myCreature.maxEndurance) {
                    user.endurance++
                    println(user.name + " regained endurance")
                } else if (random == 1 && user.endurance >= user.myCreature.maxEndurance && user.turn != 1) {
                    println(user.name + " endurance is already max")
                }
            }
        }
    }
}

class BookOfFlames : Item("Book of Flames", "Carry", "A book containing the knowledge of fire spells\n" +
        "2 Skill, 2 Mana, Blazing Bolt: A firey bolt launches at the enemy dealing 1 damage that cannot be dodged\n" +
        "3 Skill, 3 Mana, Fireball: A massive ball of fire flies at the opponent that deals 4 damage", 5){
    override fun skillOneIsActive(user: Player): String{
        if(user.skill>1 && user.mana>1 && type.equals("Carry")) {
            return "Blazing Bolt: 2 Skill, 2 Mana, 1 damage, UNDODGEABLE"
        }
        return "NULL"
    }
    override fun skillOneName(): String{
        return "Blazing Bolt"
    }
    override fun skillOne(user: Player, target: Player){
        println("A Blazing Bolt is launched at " + target.name)
        val atk = Attack(1, 10, dodgeable = false, magical = true)
        target.attacked(user, atk)
        user.spendMana(2,"SPELL")
    }
    override fun skillTwoName(): String{
        return "Fireball"
    }
    override fun skillTwoIsActive(user: Player): String{
        if(user.skill>2 && user.mana>2 && type.equals("Carry")) {
            return "Fireball: 3 Skill, 3 Mana, 4 damage"
        }
        return "NULL"
    }
    override fun skillTwo(user: Player, target: Player){
        println("A Fireball is launched at " + target.name)
        val atk = Attack(4, 10, dodgeable = true, magical = true)
        target.attacked(user, atk)
        user.spendMana(3, "SPELL")
    }
}

class TomeOfUndeath : Item("Tome of Undeath", "Carry", "A magical tome containing secrets of necromancy\n" +
        "2 Skill, 3 Mana, Flesh Mend: The user can spend 2 mana to heal 1 health\n" +
        "3 Skill, 4 Mana, Summon Undead: A creature of undeath is summoned, the creature has 5 health and 1 base damage and takes damage rather than the caster when an enemy attacks", 7){
    private var isSummoned = false
    private var zombieDamage = 1
    private var zombiePlayer = Player(false, "Zombie")
    private var zombie = Creature("Zombie",5,1,0,0,0,0,"A mindless undead thrawl")
    override fun onStartOfTurn(user: Player, target: Player) {
        if(user.turn==0){
            isSummoned=false
        }
        if(isSummoned){
            zombiePlayer.position=target.position
            val atk = zombiePlayer.attacking(target)
            println("The zombie attacks " + target.name + " with " + zombiePlayer.getCarry(true) + " dealing a potential " + zombieDamage)
            atk.damage = zombieDamage
            target.attacked(zombiePlayer, atk)
        }
    }
    override fun skillOneIsActive(user: Player): String{
        if(user.skill>1 && user.mana>1 && (user.health<user.myCreature.maxHealth || zombiePlayer.health<zombie.maxHealth) && type.equals("Carry")) {
            return "Flesh Mend: 2 Skill, 2 Mana, heals 1 health"
        }
        return "NULL"
    }
    override fun skillOneName(): String{
        return "Flesh Mend"
    }
    override fun isSummoned(): Boolean {
        return isSummoned
    }
    override fun skillOne(user: Player, target: Player){
        var decide = ""
        if(isSummoned && user.position == target.position){
            println("Heal zombie for 1 health instead of yourself? Type 'Yes' to do so")
            decide = readln()
            if(decide=="Yes"){
               zombiePlayer.health+=1
            }
        }
        if(decide!="Yes") {
            user.health++
        }
        user.spendMana(2, "SPELL")
    }
    override fun skillTwoName(): String{
        return if(isSummoned) {
            "Buff Undead"
        }else{
            "Summon Undead"
        }
    }
    override fun skillTwoIsActive(user: Player): String{
        if(user.skill>2 && user.mana>3 && type.equals("Carry")) {
            return "Summon Undead: 3 Skill, 4 Mana, 1 Undead servant"
        }else if(isSummoned && user.mana>zombieDamage && type.equals("Carry")){
            return "Buff Undead: 3 Skill, " + (zombieDamage+1) + " Mana, Undead servant gains +1 damage"
        }
        return "NULL"
    }
    override fun skillTwo(user: Player, target: Player){
        if(isSummoned){
            zombieDamage++
            user.spendMana(zombieDamage, "SPELL")
        }else {
            println("An Undead creature enters the fight!")
            zombiePlayer = Player(false, "Zombie")
            zombieDamage = 1
            zombiePlayer.newCreature(zombie)
            zombiePlayer.start()
            isSummoned = true
            user.spendMana(4, "SPELL")
        }
    }

    override fun onHit(user: Player, target: Player, atk: Attack): Attack {
        if(atk.dodgeable) {
            zombiePlayer.attacked(target, atk)
            if (zombiePlayer.health < 1) {
                isSummoned = false
            }
            atk.damage = 0
        }
        return atk
    }
}

class ClericsStaff : Item("Cleric Staff", "Carry", "A magical staff that was once the branch of a divine tree\n" +
        "2 Skill, 2 Mana, Smite: A lightning bolt comes down and smites the enemy for 3 damage" +
        "3 Skill, 4 Mana, Divine Shield: A protect shield defends the user from 2 initial damage before weakening and decreasing all consecutive attacks by 1 damage",5){
    var shieldHealth = 0
    var shieldIsActive = false
    override fun onStartOfTurn(user: Player, target: Player) {
        if (user.turn == 0) {
            shieldHealth = 0
            shieldIsActive = false
        }
    }
    override fun isSummoned(): Boolean {
        return shieldHealth>0
    }
    override fun skillOneIsActive(user: Player): String{
        if(user.skill>1 && user.mana>0 && type.equals("Carry")) {
            return "Smite: 2 Skill, 2 Mana, 3 damage"
        }
        return "NULL"
    }
    override fun skillOneName(): String{
        return "Smite"
    }
    override fun skillOne(user: Player, target: Player){
        println("Lightning bolt strikes " + target.name)
        val atk = Attack(3, 10, dodgeable = false, magical = true)
        target.attacked(user, atk)
        user.spendMana(2,"SPELL")
    }
    override fun skillTwoName(): String{
        return "Divine Shield"
    }
    override fun skillTwoIsActive(user: Player): String{
        if(user.skill>2 && user.mana>1 && type.equals("Carry")) {
            return "Divine Shield: 3 Skill, 2 Mana, summons shield"
        }
        return "NULL"
    }
    override fun skillTwo(user: Player, target: Player){
        println("A divine shield now protects " + target.name)
        shieldIsActive=true
        shieldHealth=2
        user.spendMana(4, "SPELL")
    }

    override fun onHit(user: Player, target: Player, atk: Attack): Attack {
        if(abs(user.position -target.position) > atk.range){
            atk.damage=0
            println("Attack not within range")
        }
        if(shieldIsActive && atk.damage>0){
            atk.damage-=1
            if(atk.damage==1 && shieldHealth==2){
                shieldHealth-=1
                atk.damage=0
            }else if(atk.damage>1){
                atk.damage-=shieldHealth
                shieldHealth=0
            }
        }
        return atk
    }
}

class BloodRing : Item("Ring of Blood", "Ring", "A magical ring formed by blood magic, both you and your opponent start with one less health and its user gains 1 mana when attacking", 4){
    override fun onStartOfTurn(user: Player, target: Player) {
        if(user.turn==0){
            user.takeDamage(user,1,"spell")
            target.takeDamage(user,1,"spell")
            user.mana+=2
            println("The blood ring extracts blood from both creatures dealing 1 to each! The blood gives its user 2 additional mana")
        }else{
            println("Not first turn!" + user.turn)
        }
    }

    override fun onAttack(user: Player, target: Player, atk: Attack): Attack {
        if(user.mana<user.myCreature.maxEndurance){
            println("The ring absorbs the enemies blood, giving " + user.name + " mana " + user.name + " now has " + user.mana + " mana.")
            user.mana++
        }else{
            println(user.name + " mana is max")
        }
        return atk
    }
}

class RingOfKnowledge: Item("Ring of Knowledge", "Ring", "A magical ring that enhances this creatures skill by 1", 3){
    override fun updateStats(person: Player) {
        println("SKILL BOOOST")
        person.skill++
    }

    override fun unUpdateStats(person: Player) {
        person.skill--
    }
}

class RingOfSpeed: Item("Ring of Agility", "Ring", "A magical ring that enhances this creatures agility by 3",8){
    override fun updateStats(person: Player) {
        person.agility+=3
    }

    override fun unUpdateStats(person: Player) {
        person.agility-=3
    }
}

class RingOfHealth: Item("Ring of Health", "Ring", "A magical ring that enhances this creatures health by 1", 2){
    override fun updateStats(person: Player) {
        person.health++
    }

    override fun unUpdateStats(person: Player) {
        person.health--
    }
}

class RingOfFortitude: Item("Ring of Fortitude", "Ring", "A magical ring that enhances this creatures endurance by 2", 3){
    override fun updateStats(person: Player) {
        println("ENDURANCE BOOST")
        person.endurance+=2
    }

    override fun unUpdateStats(person: Player) {
        person.endurance-=2
    }
}

class RingOfCriticalHits: Item("Ring of Critical Hits", "Ring", "A magical ring that enhances this creatures damage by 1 every other turn", 2){
    override fun onAttack(user: Player, target: Player, atk: Attack): Attack {
        if(user.turn%2==1){
            atk.damage++
        }
        return atk
    }
}

class RingOfMagic: Item("Ring of Magic", "Ring", "A magical ring that increases mana by 2", 4){
    override fun updateStats(person: Player) {
        person.agility+=2
    }

    override fun unUpdateStats(person: Player) {
        person.skill-=2
    }
}

class MartialCombat : Item("Martial Combat", "Carry", "Combat based on the skill, not the strength of the user\n1 Skill, 0 Endurance, Skillful Attack: Add skill to attack damage\n2 Skill, 0 Endurance, Skillful: Decreases endurance spent by 1",1){
    override fun getItemName(trueSight: Boolean): String {
        if(trueSight){
            return "Martial Combat"
        }
        return "no weapon"
    }
    override fun onAttack(user: Player, target: Player, atk: Attack): Attack {
        atk.damage+=user.skill
        return atk
    }

    override fun onSpendEndurance(user: Player, enduranceSpent: Int, reason: String): Int {
        if(enduranceSpent>0 && user.skill>1) {
            return enduranceSpent - 1
        }
        return enduranceSpent
    }
}

class Attack(var damage: Int, var range: Int, var dodgeable: Boolean, var magical: Boolean){}