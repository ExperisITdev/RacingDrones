# RacingDrones

Código para controlar el coche desde un ordenador.

La aplicación en Java utiliza las teclas WASD, en vez de las flechas. Al ejecutarla pide que se introduzca el puerto COM con el que se tiene que comunicar. A partir de ahí debería mandar valores al puerto COM al pulsar las teclas. 
 
El acelerador manda un valor de 1450 o 1550, y la dirección de 1000 o 2000. El valor del acelerador de momento es bajo para que sea controlable, porque fija ese valor instantáneamente. Una vez hayamos comprobado que funciona, podemos intentar mejorar eso implementando distintos algoritmos de aceleración.
