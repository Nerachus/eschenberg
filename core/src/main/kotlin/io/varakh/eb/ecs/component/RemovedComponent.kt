package io.varakh.eb.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class RemovedComponent : Component, Pool.Poolable {
    var delay = 0f

    override fun reset() {
        delay = 0f
    }

    companion object {
        val mapper = mapperFor<RemovedComponent>()
    }
}