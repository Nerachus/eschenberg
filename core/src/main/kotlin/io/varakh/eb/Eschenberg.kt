package io.varakh.eb

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.viewport.FitViewport
import io.varakh.eb.ecs.system.*
import io.varakh.eb.screen.EschenbergScreen
import io.varakh.eb.screen.GameScreen
import io.varakh.eb.screen.LoadingScreen
import io.varakh.eb.screen.MenuScreen
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger

const val UNIT_SCALE = 1 / 16f
const val V_WIDTH = 16f
const val V_HEIGHT = 9f

private val log = logger<Eschenberg>()

class Eschenberg : KtxGame<EschenbergScreen>() {

    private val graphicsAtlas by lazy { TextureAtlas(Gdx.files.internal("graphics/graphics.atlas")) }

    val batch by lazy { SpriteBatch() }
    val viewport = FitViewport(16f, 9f)
    val engine: PooledEngine by lazy {
        PooledEngine().apply {
            addSystem(PlayerInputSystem(viewport))
            addSystem(MoveSystem())
            addSystem(PlayerAnimationSystem(
                    regionUp = graphicsAtlas.findRegion("HeroKnight_Idle", 0),
                    regionRight = graphicsAtlas.findRegion("HeroKnight_Idle", 3),
                    regionDown = graphicsAtlas.findRegion("HeroKnight_Idle", 5),
                    regionLeft = graphicsAtlas.findRegion("HeroKnight_Idle", 7)
            ))
            addSystem(RenderSystem(batch, viewport))
            addSystem(RemoveSystem())
        }
    }

    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        log.debug { "Create game instance" }
        addScreen(LoadingScreen(this))
        addScreen(MenuScreen(this))
        addScreen(GameScreen(this))
        setScreen<GameScreen>()
    }

    override fun dispose() {
        super.dispose()
        log.debug { "Sprites in batch: ${batch.maxSpritesInBatch}" }
        batch.dispose()
        graphicsAtlas.dispose()
    }
}