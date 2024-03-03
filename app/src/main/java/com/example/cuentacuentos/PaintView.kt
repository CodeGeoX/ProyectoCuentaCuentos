package com.example.cuentacuentos

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.cuentacuentos.PaintActivity.Companion.paintBrush
import com.example.cuentacuentos.PaintActivity.Companion.path

class PaintView : View {

    var params : ViewGroup.LayoutParams? = null

    companion object {
        var pathList = ArrayList<Path>() // Lista de rutas de dibujo
        var colorList = ArrayList<Int>() // Lista de colores de los trazos
        var currentBrush = Color.BLACK // Color del pincel actual, empieza por el color negro por defecto
    }


    constructor(context: Context) : this(context, null){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    // Método de inicialización de la vista
    private fun init(){
        paintBrush.isAntiAlias = true // Activa el suavizado de bordes
        paintBrush.color = currentBrush // Establece el color del pincel
        paintBrush.style = Paint.Style.STROKE // Establece el estilo de trazo como línea
        paintBrush.strokeJoin = Paint.Join.ROUND // Establece el estilo de unión de trazos redondeado
        paintBrush.strokeWidth = 8f // Establece el ancho del trazo

        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT) // Define los parámetros de diseño de la vista
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x // Posición horizontal del evento táctil
        var y = event.y // Posición vertical del evento táctil

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y) // Establece el punto inicial del camino de dibujo
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x,y) // Dibuja una línea desde el último punto hasta el nuevo punto
                pathList.add(path) // Agrega el camino de dibujo a la lista de caminos
                colorList.add(currentBrush) // Agrega el color del trazo a la lista de colores
            }
            else -> return false
        }
        postInvalidate()
        return false
    }
    override fun onDraw(canvas: Canvas) {

        for (i in pathList.indices) {
            paintBrush.setColor(colorList[i]) // Establece el color del pincel
            canvas.drawPath(pathList[i], paintBrush) // Dibuja el camino en el lienzo
            invalidate() // Invalida la vista para solicitar un nuevo dibujo en el lienzo
        }

    }

}