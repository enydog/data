using Toybox.Sensor;
import Toybox.SensorLogging;    
class UbicacionEspacial
{
    hidden var mFilter = null;
    hidden var mX = null;
    hidden var mY = null;
    hidden var mZ = null;
    
    function initialize() {
        mFilter = new Sensor.FirFilter({:coefficients=>[0.20,0.15,0.6], :gain=>1.2);
        //Finite Impulse Response (FIR)    
    }
 
    function Activar() {
        var maxSampleRate = Sensor.getMaxSampleRate(); 
      
        var options = {:period => 4000, :sampleRate => maxSampleRate, :enableAccelerometer => true};
        try {
            Sensor.registerSensorDataListener(method(:accelHistoryCallback), options);
        }
        catch(e) {
            System.println(e.getErrorMessage());
        }
    }
     
    function getPuntoEspacial(sensorData) {
        mX = mFilter.apply(sensorData.accelerometerData.x);
        mY = sensorData.accelerometerData.y;
        mZ = sensorData.accelerometerData.z;
    
        Toybox.System.println("FIR - Filtarados");            
        Toybox.System.println("X axis: " + mX);
        Toybox.System.println("Y axis: " + mY);
        Toybox.System.println("Z axis: " + mZ);
    }
    
    function Destroy() {
        Sensor.unregisterSensorDataListener();
    }
}
