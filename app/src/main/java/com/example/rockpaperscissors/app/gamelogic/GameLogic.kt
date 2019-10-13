package com.example.rockpaperscissors.app.gamelogic

import com.example.rockpaperscissors.app.model.Game

class GameLogic : Comparator<Game.Choice> {

    override fun compare(p0: Game.Choice?, p1: Game.Choice?): Int {
        return if (p0 == p1) {
            Game.Result.DRAW.value
        } else if ((p0 == Game.Choice.ROCK && p1 == Game.Choice.SCISSORS) ||
            (p0 == Game.Choice.PAPER && p1 == Game.Choice.ROCK) ||
            (p0 == Game.Choice.SCISSORS && p1 == Game.Choice.PAPER)
        ) {
            Game.Result.WIN.value
        } else {
            Game.Result.LOSS.value
        }
    }
}