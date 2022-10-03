# Queue Theory Simulator

The project runs simulation on event queue M/M/1 with periodically shutdowns server from handling incoming messeges.

Main assumptions:
- the server uptime is modeled by a random vairable C<sub>on</sub> with an exponential distribution, where the mean E(C<sub>on</sub>)=40[s],
- interuptions in server operation are modeled by a random variable C<sub>off</sub> with an exponential distribution, where the mean E(C<sub>off</sub>)=35[s],
- handling of a request interrupted by server shut down is completed after its resume,
- average time of message handling in server D=0.125[s]

### How it works
Application runs two simulations: one without server shutdowns, second with them.

Based on received results generates plots, wich contains lower/upper confidence, average and analytical value of delay.


![Sim1-250s](https://user-images.githubusercontent.com/26869741/193630023-42ab7f06-44b5-42cb-931d-fdca035ceaf7.png)
![Sim2-250s](https://user-images.githubusercontent.com/26869741/193630117-e9e8a70c-9755-4594-90ee-9cffefd57631.png)
