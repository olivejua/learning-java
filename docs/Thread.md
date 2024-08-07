```
- 운영체제의 Thread는 어떻게 동작하는가?
	- 운영체제, 코어 이런쪽까지 쭉 내려갔다가 올라오면 좋을것 같다
- 운영체제 스레드와 자바 스레드는 어떤 관계가 있는지
	- 이 둘이 같은거야, 다른거야. 얘가 하나 생기면 다른애도 생기는거야 아니면 서로서로 Share하는거야.
- Java는 Stop-the-World로 왜 모든 스레드를 멈추는방법을 선택해야만 했을까
- 키워드 : Green Thread, Native Thread
```


> [!tip] 참고 링크
> - https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-2.html
> - https://blog.ycrash.io/overview-jvm-threads-multithreading-java/
> - https://techblog.woowahan.com/15398/



### H3) 프로그램 실행 과정
- 실행하라고 운영체제에 전달됨
- 프로세스를 생성한다.
    - 프로세스 제어블록 (PCB) 생성: 새로운 프로세스에 대한 정보를 저장함
        - 프로세스 ID
        - 프로세스 상태
        - 프로그램 카운터
        - 레지스터 상태
        - 메모리 관리 정보
    - 메모리 할당 : 프로세스의 코드, 데이터, 힙, 스택을 위한 메모리를 할당한다.
    - 프로그램 로드: 실행파일의 코드를 메모리에 로드한다.
- 초기 스레드 생성
    - 프로세스가 생성되면, 운영체제는 해당 프로세스의 첫번째 스레드(주 스레드 또는 메인스레드)를 생성한다.
    - 스레드 제어 블록 (TCB) 생성
    - 프로그램 카운터 초기화: 프로그램의 시작 주소로 설정
    - 스택 초기화 : 스레드의 스택을 설정
    - 레지스터 초기화: 스레드의 레지스터 상태를 초기화
- 스케줄링
    - Ready Queue에 추가: 생성된 스레드를 Ready 상태로 설정하고, Ready Queue에 추가한다.
    - 스케줄러: 운영체제의 스케줄러가 Ready Queue에서 스레드를 선택하여 CPU에 할당한다.
- 스레드를 실행: 선택된 스레드는 CPU에서 실행 시작한다.

> [!information] 스레드의 구성요소
> - 스레드 ID
> - 프로그램 카운터
    > 	- 현재 실행중인 명령어의 주소를 가리킴. 스레드가 실행을 재개할 때 어느 명령어부터 실행해야하는지를 결정함.
> - 레지스터 집합
    > 	- 스레드가 실행 중에 사용하는 CPU 레지스터의 상태를 저장함. 레지스터에는 일반 목적 레지스터, 스택 포인터, 플래스 레지스터 등이 포함됨
> - 스택
    > 	- 스레드가 함수 호출, 로컬 변수, 리턴 주소등을 저장하는 메모리 영역
> - 스레드 상태
    > 	- 스레드의 현재 상태 (New / Ready / Running / Waiting / Terminated)
> - 우선순위
    > 	- 스레드의 실행 우선순위. 우선순위를 참고하여 어떤 스레드를 먼저 실행할지 결정함
> - 스레드의 특정 데이터
    > 	- 스레드가 필요로하는 추가적인 정보나 데이터
> - 스레드 스케줄링 정보
    > 	- 예: 마지막 실행시간, CPU 사용 시간


#### H4) Java 프로그램 실행 및 메인스레드 생성과정
###### H6) 1. JVM 프로세스 생성 (<- CLI, GUI 에 의해 실행)
	운영체제는 JVM을 실행하기 위해 새로운 프로세스를 생성
- 프로세스 제어블록(PCB) 생성: 운영체제는 JVM 프로세스에 대한 PCB를 생성한다.
- 메모리 할당: JVM 코드, 데이터, 힙, 스택을 위한 메모리를 할당한다.
- JVM 코드 로드: JVM 실행 파일의 코드를 메모리에 로드한다.
###### H6) 2. JVM 초기화
- 클래스 로더 초기화
- JVM 내부 데이터 구조 초기화: 메모리 관리, 스레드 관리 등의 내부 구조를 초기화
###### H6) 3. 메인스레드 생성
JVM은 `main`메서드를 실행하기 위한 메인스레드 생성
- 스레드 제어블록 (TCB) 생성
- 프로그램 카운터 설정: 프로그램 카운터를 `MyProgram`의 `main` 메서드의 시작 주소로 설정
- 스택 초기화: 메인스레드의 스택을 설정
###### H6) 4.메인 스레드 준비
- Ready Queue에 추가 : 메인 스레드는 Ready 상태로 설정되고 Ready Queue에 추가됨
###### H6) 4. `main` 메서드 실행
메인스레드는 main메서드를 실행한다.
- 스케줄러 선택: 스케줄러는 Ready Queue에 메인스레드가 선택한다.
- CPU 할당: 메인 스레드가 CPU를 할당받아 실행한다.

##### H5) HTTP 요청으로 메인스레드가 아닌 스레드 B가 객체를 생성하여 힙영역이 늘어나면 프로세스의 힙영역 업데이트는 어떻게 하는가?
1. 스레드 B 실행: HTTP 요청으로 스레드B가 생성되고 실행됨
2. 객체 생성: 스레드 B가 객체 생성하면서 힙 영역에 메모리를 할당한다. 그러면 힙영역의 사용량이 증가한다.
3. 힙 영역 업데이트: 힙 영역에 객체가 할당될 때 **JVM은 힙 사용량을 업데이트한다.** 즉 힙 사용량 업데이트는 스레드 B가 아닌 JVM이 메모리 관리 시스템에 의해 자동으로 수행된다.
4. 가비지 컬렉션: JVM은 주기적으로 가비지 컬렉션을 수행하여 더이상 사용되지 않는 객체를 정리하고, 힙 영역을 효율적으로 관리한다.

#### H4) JVM을 실행하는 스레드가 따로 있는건가?
> JVM은 여러 스레드로 구성된 복잡한 시스템이다. JVM 자체가 운영체제 위에서 하나의 프로세스로 실행되며, JVM 내부에서 여러 스레드가 동시에 동작한다. JVM은 자체적으로 관리하는 여러 스레드를 통해 작업을 수행한다. 여기에는 JVM을 초기화하고, 관리하고, 애플리케이션 스레드를 실행하는 스레드가 포함된다.

##### H5) JVM 내 스레드 구조
###### 1. JVM 메인 스레드
- JVM을 시작하고 초기화하는 스레드
- 이 스레드는 `main` 메서드를 호출하여 애플리케이션의 메인 스레드를 시작한다.
###### 2. 애플리케이션 스레드
- `main` 메서드를 포함한 사용자 애플리케이션 코드를 실행하는 스레드
- 예를 들어, 애플리케이션에서 HTTP 요청을 처리하기 위해  생성하는 스레드B 등이 포함된다.
###### 3. 가비지 컬렉션 스레드
- JVM의 메모리 관리를 담당하는 스레드
- 힙 영역의 사용되지 않는 객체를 정리한다.
###### 4. JIT 컴파일러 스레드
- JVM의 Just-In-Time (JIT) 컴파일러가 사용하는 스레드
- 바이트코드를 네이티브 코드로 컴파일하여 성능을 최적화한다.
###### 5. 다른 내부 JVM 관리 스레드
- 다양한 내부 작업을 수행하는 추가적인 관리 스레드가 있다. 예를 들어, 클래스 로딩, 프로파일링, 모니터링 등을 수행한다.

##### H5) JVM 내 스레드 사용흐름
###### 1. JVM 초기화
- JVM 메인스레드
    - 생성 시점: JVM이 시작될 때 운영체제에 의해 생성된다.
    - 역할: JVM 초기화 작업을 수행하고, 애플리케이션의 `main` 메서드를 호출하여 애플리케이션이 실행을 시작한다.
    - 종료: JVM이 종료될 때 종료됨
###### 2. 애플리케이션 실행
- 애플리케이션 메인 스레드
    - 생성시점: JVM 메인 스레드가 애플리케이션의 `main` 메서드를 호출할 때 생성된다.
    - 역할: 애플리케이션의 메인 로직을 실행한다. 예를 들어, 사용자가 작성한 Java 코드가 실행된다.
    - 종료: 애플리케이션의 `main` 메서드 실행이 완료되면 종료된다.
- 애플리케이션 워커 스레드
    - 생성시점: 애플리케이션 코드에서 명시적으로 스레드를 생성할 때 생성된다. 예를 들어 `new Thread(() -> {}).start();` 호출할 때
    - 역할 : 비동기 작업, 병렬처리, I/O 작업 등 다양한 애플리케이션 로직을 처리한다.
    - 종료: 작업이 완료되거나 종료되거나, 명시적으로 스레드가 종료되도록 요청할 때 종료된다.
###### 3. 메모리 관리
- 가비지 컬렉션 스레드
    - 생성시점: JVM 초기화시 생성됨
    - 힙 영역에서 사용되지 않는 객체를 정리하며 메모리를 회수한다. 주기적으로 또는 메모리 부족시 가비지 컬렉션을 수행한다.
    - 종료: JVM이 종료될 때 종료된다.
###### 4. JIT 컴파일
- JIT 컴파일러 스레드
    - 생성시점: JVM 초기화시 생성된다.
    - 역할: 자주 실행되는 바이트코드를 네이티브 코드로 컴파일하여 실행 성능을 최적화한다.
    - 종료: JVM이 종료될 때 종료된다.
###### 5. 기타 관리 스레드
- 클래스로더 스레드
    - 생성시점: JVM 초기화시 또는 클래스 로딩이 필요한 시점에 생성된다.
    - 역할: Java 클래스 파일을 메모리에 로드하고, 바이트코드를 해석하여 JVM 내에서 실행가능하도록 준비한다.
    - 종료: 클래스 로딩 작업이 완료되면 종료된다.
- 시그널 핸들러 스레드
    - 생성시점: JVM 초기화시 생성된다.
    - 역할: 운영체제의 신호(Signal)를 처리한다. 예를 들어, 종료 신호 (SIGTERM)를 처리하여 JVM을 안전하게 종료한다.
    - 종료: JVM이 종료될 때 종료된다.
###### 6. 종료과정
	JVM이 종료될 때, 모든 애플리케이션 스레드와 JVM 내부 관리 스레드가 정리된다. JVM은 다음과 같은 단계를 거쳐 종료된다.
1. 애플리케이션 스레드 종료 : 애플리케이션 메인스레드와 워커 스레드가 종료된다.
2. 관리 스레드 종료: 가비지 컬렉션 스레드, JIT 컴파일러 스레드, 클래스 로더 스레드, 시그널 핸들러 스레드 등 JVM 내부 관리 스레드가 종료된다.
3. JVM 종료: 모든 스레드가 종료되면 JVM 자체가 종료되고, 운영체제는 JVM 프로세스를 정리한다.


> [!question]
> - 병렬로 실행하겠다하는 것은 그냥 Running-Ready 상태를 반복하며 하는건가?

### H3) new Thread()를 하면 운영체제의 스레드가 생성되는 걸까?
1. `new Thread()` 호출
    1. new Thread 호출하면 Thread 객체는 생성되었지만 운영체제의 스레드는 생성되지 않는다. (`Thread thread = new Thread();`)
2. `start()` 메서드 호출
    1. 운영체제 수준의 스레드를 생성하려면 `start()` 메서드를 호출해야한다. (`thread.start()`)
3. JVM의 스레드 생성과정
    1. 스레드 초기화 : JVM은 새로운 스레드를 생성하기 위해 초기설정을 수행한다. 여기에는 스레드 이름, 우선순위, 스레드 그룹 등의 설정이 포함된다.
    2. 네이티브 메서드 호출: JVM은 네이티브 메서드를 호출하여 운영체제 수준의 스레드를 생성한다. 네이티브 메서드의 각 플랫폼에 특화된 코드로 구현되며, JVM은 이를 통해 운영체제와 상호작용한다.
    3. 운영체제의 스레드 생성: 네이티브 메서드는 운영체제의 스레드 생성 API를 호출한다. 예를 들어, Windows에는 `CreateThread`함수를 사용하고, Unix 계열 시스템에서는 `pthread_create` 함수를 사용한다.
4. 운영체제의 스레드
    1. 운영체제는 스레드 생성 요청을 받아들이고, 새로운 스레드를 생성한다. 이 스레드는 JVM에 의해 관리되며, Java의 `Thread` 객체와 매핑된다. (==Java에 Thread 매핑을 어떻게 하지? 어디서 확인해볼 수 있지?==)
    2. TCB 생성 : 운영체제는 새로운 스레드 제어블록(TCB)을 생성하여 스레드의 상태와 컨텍스트 관리를 한다.
    3. 스택 할당 : 스레드를 위한 스택 메모리를 할당한다. (==어떻게 할당하고 있지? Java에서 확인할 수 있나?==)
    4. 실행 준비: 새로 생성된 스레드는 실행 준비 상태가 되며, 운영체제의 스케줄러에 의해 스케줄링된다.
5. 스레드 실행
    1. 운영체제의 스레드는 생성된 스레드를 스케줄링하여 CPU에서 실행할 기회를 부여한다. 이 과정에서 JVM은 해당 스레드가 실행할 `run()` 메서드를 호출한다.
    2. 사용자가 정의한 `run()`메서드의 코드가 실행된다. 이 코드는 새로운 스레드에서 병렬로 실행되며, 메인 스레드와 독립적으로 작업을 수행할 수 있다. (==병렬 스레드로 수행되는되는것과 싱글스레드로 수행하는 것에 CPU 내에서 어떤 변화가 있는거고, Java나 다른 언어들은 어떻게 시키는걸까?==)
6. 스레드 종료
    1. 스레드의 `run()`메서드가 종료되면, 스레드는 종료 상태로 전환된다. 운영체제 스레드가 사용한 리소스를 해제하고, JVM은 Java 수준에서 스레드의 상태를 업데이트한다.


#### H4) 스레드 생성의 네이티브 메서드 호출시 운영체제단에서 제공해주는 코드인가?
```java
public class Thread {
	public synchronized void start() {  
    ...
    
    try {  
        start0();  
        ...
    } finally {  
        ...
    }    
    
	private native void start0();
}
```

JVM에서 사용하는 네이티브 메서드는 주로 **JVM을 개발한 조직**에서 구현한다. 대표적으로는 Oracle, OpenJDK 커뮤니티 등이 있다.

Native Method 구현과정
1. 설계 및 사양 정의: 네이티브 메서드를 구현하려면 먼저 메서드의 동작과 인터페이스를 설계한다.
2. 구현: 정의된 사양에 따라 C 또는 C++ 언어로 네이티브 메서드를 구현한다. 이는 운영체제의 API를 호출하여 필요한 기능을 수행한다.
3. 빌드 및 통합: 구현된 네이티브 메서드는 JVM의 소스 코드와 함께 빌드된다. 이 과정에서 각 운영체제와 플랫폼에 맞는 바이너리가 생성된다.
4. 테스트: 네이티브 메서드는 철저한 테스트를 거쳐 안정성과 성능을 검증 받는다. 이를 통해 JVM과의 상호작용에서 발생할 수 있는 문제를 최소화한다.
5. 배포: 테스트가 완료된 네이티브 메서드는 최종적으로 JVM의 배포 패키지에 포함되어 사용자에게 제공된다.

##### H5) 왜 Java로 안하고 C나 C++로 구현했을까?
> - 운영체제 및 하드웨어 접근
    > 	- C와 C++은 시스템 프로그래밍 언어로, 운영체제와 하드웨어 자원에 대한 직접 접근이 가능하다. 이는 저수준의 작업을 수행하거나 고성능 요구사항을 충족시킬 때 필수적이다.
> - 성능
    > 	- C와 C++은 기계어에 가깝고 컴파일러 최적화를 통해 매우 빠른 속도를 제공한다. 네이티브 메서드는 JVM의 성능에 중요한 영향을 미치므로, 성능이 중요한 부분은 C나 C++로 구현된다.
> - 운영체제 API 호출
    > 	- 운영체제에서 제공하는 스레드, 파일시스템, 네트워크, 메모리 관리 등 다양한 기능은 주로 C API를 통해 제공된다. Java에서 이러한 기능을 사용하기 위해서 네이티브 코드를 통해 운영체제 API를 호출해야한다.
> - 기존코드 재사용
    > 	- 많은 시스템 라이브러리와 기존 소프트웨어가 C/C++로 작성되어 있다. 이를 재사용하여 상호운용하기 위해 네이티브 메서드가 필요하다.

##### H5) 각 운영체제에 맞는 네이티브 로직으로 다운받는걸까 또는 JVM이 실행시 운영체제 맞는 네이티브 메서드를 로드해와서 사용하는걸까?
> Java 애플리케이션을 배포할 때 일반적으로 다음 두가지 방법이 사용된다.
> 1. 운영체제별로 다른 JVM 배포 : 각 운영체제에 맞게 빌드되어 배포됨
> 2. JVM 내에서 운영체제 감지 및 로딩 : JVM이 실행시 운영체제를 감지하고 해당 운영체제에 맞는 네이티브 메서드를 로드하여 실행함. 이는 주로 JNI(Java Native Interface)라이브러리를 사용할 때 많이 사용됨

예시: JVM 실행시 네이티브 코드 로딩
1. 운영체제 감지
    1. JVM은 실행환경의 운영체제를 감지한다. 이는 기본적으로 시스템콜을 통해 운영체제의 정보를 가져오기 과정이다.
2. 네이티브 라이브러리 로드
    1. 운영체제를 감지한 후 네이티브를 호출하면, JVM은 로드된 네이티브 라이브러리를 로드한다.
3. 네이티브 메서드 실행
    1. Java 코드에서 네이티브 메서드를 호출하면 JVM은 로드된 네이티브 라이브러리 함수를 호출한다. 이 과정은 주로 JNI를 통해 이루어진다.

#### H4) Java에서 운영체제 API를 직접호출할 수 없는 이유
> 이유는 주로 Java 언어의 설계 철학과 기술적인 제한 때문이다.
> **1. Java의 설계 철학**
> Java는 플랫폼 독립성을 중요시하는 언어이다. 플랫폼 독립성은 "Write Once, Run Anywhere"라는 슬로건으로 대표되며, 이는 어떤 운영체제에서든 동일하게 동작하도록 보장하는 것을 의미한다.
> 	- 바이트코드와 JVM: Java 컴파일러는 Java 소스코드를 플랫폼 독립적인 바이트코드로 컴파일한다.
> 	- 추상화: Java는 운영체제나 하드웨어에 대한 세부사항을 추상화하여 개발자가 신경쓰지 않도록 한다. 이러한 추상화는 일관된 프로그래밍환경을 제공한다.
> **2. 기술적 제한**
> 	- 네이티브 코드와의 차이: 운영체제 API는 일반적으로 C/C++와 같은 시스템 프로그래밍 언어로 작성되어 있다. Java는 이러한 네이티브 코드를 직접 호출할 수 있는 메커니즘이 없다. 대신 JNI와 같은 인터페이스를 사용하여 네이티브 코드를 호출해야한다. (==다른언어로 구현된 로직도 Java의 바이트코드로 로드되나? 어떻게 하나의 배포물이 나오는 걸까?==)
> 	- 메모리 관리: Java의 자동 메모리 관리(가비지 컬렉션)를 제공한다. 반면, 운영체제 API는 종종 수동 메모리관리(Malloc/Free 등)를 요구한다. 이러한 메모리 관리 방식의 차이는 Java가 운영체제 API와 직접 상호작용하는 것을 어렵게 만든다. (==다른 언어에서 사용하는 운영체제 자원도 Java 프로그램에서 사용하는 것으로 모두 포함해서 하는 거겠지?==)
> 	- 보안: Java는 보안성을 높이기 위해 샌드박스 환경에서 실행된다. Java 애플리케이션이 운영체제의 중요한 부분에 직접 접근하는 것을 제한한다. 운영체제 API에 대한 직접 호출은 이러한 보안모델을 깨트릴 수 있다.

#### H4) Java Thread와 운영체제 스레드를 어떻게 매핑하는 거지?
> Java에서 `Thread` 객체를 운영체제의 스레드와 매핑하는 과정은 JVM의 내부 매커니즘에 의해 관리된다. 이 과정은 여러 단계로 이루어지며, 각 단계에서는 Java의 `Thread` 객체가 운영체제의 스레드와 연결된다.

###### 1. Java에서 Thread 객체 생성
먼저, Java 프로그램에서 새로운 `Thread` 객체를 생성한다.
```java
Thread myThread = new Thread(() -> {
	System.out.println("Hello From the new thread");
})
```
이 코드는 새로운 `thread`를 생성하지만, 아직 실제 스레드가 생성된 것은 아니다.
###### 2. start() 메서드 호출
`start()`메서드를 호출하면 실제로 운영체제의 스레드가 생성된다.
```java
myThread.start()
```

###### 3. JVM 내부의 네이티브 메서드 호출
`start()` 메서드는 내부적으로 네이티브 메서드인 `start0()`을 호출하여 운영체제의 스레드를 생성한다.
```java
public class Thread {
	public synchronized void start() {
		if (threadStatus != 0) {
			throw new IllegalThreadStateException();
		}
		
		group.add(this);
		start0();
	}
	
	private native void start0();
}
```
###### 4. 네이티브 메서드와 운영체제 스레드를 생성
`start0()` 메서드는 JVM의 네이티브 코드로 구현되어 있으며, 운영체에의 스레드 생성 API를 호출한다. 예를 들어, Unix/Linux에서는 `pthread_create`를 호출하고, Windows에서는 `CreateThread`를 호출한다.

네이티브 코드 예시 (가상코드)
```c
JNIEXPORT void JNICALL
Java_java_lang_Thread_start0(JNIEnv *env, jobject javaThread) {
	// 운영체제의 스레드 생성 API 호출
	// pthread_create 또는 CreateThread 호출
	JavaThread *jt = malloc(sizeof(JavaThread));
	jt->env = env;
	jt->javaThread = (*env)->NewGlobalRef(env, javaThread);
	pthread_create(&jt->nativeThread, NULL, threadEntryPoint, jt);
}
```
###### 5. 스레드 구조체와 매핑
JVM은 운영체제의 스레드를 생성할 때 Java의 `Thread` 객체와 운영체제의 스레드를 연결하기 위해 특정 구조체를 사용한다. 이 구조체는 스레드 관련 정보를 관리한다.

```c
typedef struct {
	JNIEnv *env;
	jobject javaThread;
	pthread_t nativeThread; // 또는 HANDLE (Windows)
} JavaThread

Java_java_lang_Thread_start0(JNIEnv *env, jobject javaThread) {
	// 운영체제의 스레드 생성 API 호출
	// pthread_create 또는 CreateThread 호출
	JavaThread *jt = malloc(sizeof(JavaThread));
	jt->env = env;
	jt->javaThread = (*env)->NewGlobalRef(env, javaThread);
	
	pthread_create(&jt->nativeThread, NULL, threadEntryPoint, jt);
	
	//Store the native thread ID in the Java object
	jclass threadClass = (*env)->GetObjectClass(env, javaThread);
	jfieldID nativeThreadIdField = (*env)->GetFieldID(env, threadClass, "eetop", "J"); //native Thread 식별자 이름 저장
	(*env)->SetLongField(env, javaThread, nativeThreadField, (jlong)jt->nativeThread);
}
```
###### 6. 운영체제 스레드와 Java Thread 객체의 상호작용
운영체제의 스레드가 생성되면, 이 스레드는 JVM의 특정 엔트리 포인트에서 실행을 시작한다. 이 엔트리 포인트는 Java의 `run()` 메서드와 연결되어 있다.

스레드 엔트리 포인트 (가상의 코드)
```c
void *threadEntryPoint(void *arg) {
	JavaThread *jt = (JavaThread *)arg;
	//Java run() 메서드 호출
	JNIEnv *env = jt->env;
	jclass threadClass = (*env)->GetObjectClass(env, jt->javaThread)
	jmethodID runMethod = (*env)->GetMethodID(env, threadClass, "run", "()V");
	(*env)->CallVoidMethod(env, jt->javaThread, runMethod);
	
	//Cleanup
	(*env)->DeleteGlobalRef(env, jt->javaThread);
	free(jt);
	return NULL;
}
```
###### 7. 스레드 실행
운영체제의 스레드는 이제 Java의 `run()` 메서드를 실행한다. 이 메서드가 종료되면, 스레드는 종료되고, JVM은 스레드의 종료상태를 관리한다.










##### H5) Native Thread가 뭐지?
> 운영체제에서 직접 관리하는 스레드이다. 운영체제는 스레드의 생성, 스케줄링, 동기화, 종료 등을 처리한다. Java에서는 `Thread` 클래스를 통해 스레드를 생성하고 관리할 수 있지만, 실제로는 JVM이 운영체제의 네이티브 스레드를 생성하고 관리한다.


#### H4) ReadyQueue에 들어가는건 프로세스인가 스레드인가?
> 운영체제의 대기 큐에 있는 것은 정확히 말하면 **스레드**이다. 현대의 많은 운영체제는 스레드를 기본적인 실행단위로 관리한다.

- **프로세스**
    - 실행 중인 프로그램의 인스턴스이다.
    - 각 프로세스는 하나 이상의 스레드를 가질 수 있따.
    - 프로세스는 독립적인 메모리 공간을 가진다.
- **스레드**
    - 프로세스 내에서 실행되는 가장 작은 단위의 실행 단위이다.
    - 스레드는 같은 프로세스 내에서 코드, 데이터, 힙 영역을 공유한다.
    - 각 스레드는 독립적인 실행흐름을 가지며, 스택과 레지스터 집합을 개별적으로 가진다.

##### H5) 운영체제의 대기 큐 (Ready Queue)
- 대기큐에는 실행 준비가 된 스레드들이 포함된다.
- 스케줄러는 이 큐에서 스레드를 선택하여 CPU 코어에 할당한다.

##### H5) 스레드 관리 방식
- 단일 스레드 프로세스
    - 프로세스와 스레드가 사실상 동일하게 취급된다.
    - Ready Queue에 기본 스레드가 포함된다.
- 멀티 스레드 프로세스
    - 멀티 스레드의 프로세스의 경우, 각 스레드가 별도로 Ready Queue에 포함된다.
    - 프로세스 A가 3개의 스레드를 가지고 있다면, 이 3개의 스레드는 각각 Ready Queue에 들어간다.
#### H4) 스레드가 개별적으로 실행된다면 스레드 상태와 프로세스상태는 어떻게 관리되는 걸까?
###### H6) 스레드 상태
- New : 스레드가 생성된 상태
- Ready : 스레드가 실행 준비된 대로, CPU 할당을 기다리고 있는 상태
- Running : 스레드가 CPU에서 실행 중인 상태
- Waiting : 스레드가 I/O 작업 등으로 인해 대기 중인 상태
- Terminated : 스레드가 실행을 완료하고 종료된 상태
###### H6) 프로세스 상태
	프로세스 상태는 그 프로세스의 모든 스레드의 상태를 종합하여 결정된다.
- New : 프로세스가 생성된 상태
- Ready : 프로세스 내 하나 이상의 스레드가 `Ready` 상태에 있는 경우
- Running : 프로세스 내 하나 이상의 스레드가 `Running` 상태에 있는 경우
- Waiting : 프로세스 내 모든 스레드가 `Waiting` 상태에 있는 경우
- Terminated : 프로세스 내 모든 스레드가 종료된 상태

##### H5) 상태 관리
**1. 스레드 상태 관리**
- 각 스레드는 독립적으로 상태를 전환한다.
- 예를 들어, 스레드 A가 I/O 작업을 요청하면, `Waiting` 상태로 전환되고, 다른 스레드 B가 CPU에서 실행중일 수 있다.
  **2. 프로세스 상태 관리**
- 프로세스 상태는 그 프로세스의 모든 스레드의 상태를 종합하여 관리된다.
- 예를 들어, 프로세스 A가 3개의 스레드를 가지고 있고, 이 중 하나의 스레드만 `Running` 상태라면 프로세스 A는 `Running` 상태로 간주된다.


> [!question]
> - java에서 스레드풀로 스레드를 많이 생성해서 두던데 그럼 그건 생성상태에서 그냥 있는건가?

### H3) 스레드들의 TCB의 위치
> 스레드 제어 블록(TCB)은 운영체제가 각 스레드를 관리하기 위해 사용하는 데이터 구조로, 스레드의 상태와 실행 정보를 저장한다. TCB는 JVM 자체가 아닌 운영체제에 의해 관리되며, 운영체제의 커널 메모리 영역에 위치한다.

#### H4) JVM 스레드 관리
- JVM 운영체제 협력
    - JVM은 운영체제의 스레드 API를 생성하고 관리한다. 예를 들어 POSIX기반 시스템에서는 `pthread_create`함수를 사용하여 스레드를 생성할 수 있다.
    - 운영체제는 스레드를 생성할 때 TCB를 초기화하고, 커널 메모리 영역에 TCB를 저장한다.
- 스레드 생성
    - JVM이 스레드를 생성하면, 운영체제는 해당 스레드의 TCB를 생성하고 초기화한다. TCB는 운영체제의 커널 메모리 영역에 위치한다.
- 스레드 실행
    - 스레드가 실행될 때, 운영체제는 TCB에 저장된 정보를 사용하여 스레드의 상태를 관리한다. 예를 들어, 스레드가 실행 중인 동안 레지스터 상태와 프로그램 카운터를 TCB에 저장하고 복원한다.
- 컨텍스트 스위칭
    - 운영체제는 여러 스레드를 효율적으로 실행하기 위해 컨텍스트 스위칭을 수행한다. 컨텍스트 스위칭 시  운영체제는 현재 실행 중인 스레드의 상태를 TCB에 저장하고 다음에 실행할 스레드의 상태에 TCB에서 복원한다.

```
+--------------------------------------+
|            운영체제 메모리              |
+--------------------------------------+
| 커널 메모리 영역 (Kernel Memory)      |
| - 스레드 제어 블록 (TCB)              |
|   +------------------------------+  |
|   | 스레드 A의 TCB                |  |
|   | - 스레드 ID, 레지스터 상태 등  |  |
|   +------------------------------+  |
|   | 스레드 B의 TCB                |  |
|   | - 스레드 ID, 레지스터 상태 등  |  |
|   +------------------------------+  |
|   | ...                          |  |
|   +------------------------------+  |
+--------------------------------------+
| 사용자 메모리 영역 (User Memory)      |
| - JVM 메모리                          |
|   +--------------------------------+  |
|   | JVM 힙 (Heap)                   |  |
|   | - 객체와 배열 할당               |  |
|   +--------------------------------+  |
|   | JVM 스택 (Stacks)               |  |
|   | - 각 스레드의 스택 메모리        |  |
|   +--------------------------------+  |
|   | 메소드 영역 (Method Area)       |  |
|   | - 클래스 정보, 메소드 정보 등    |  |
|   +--------------------------------+  |
+--------------------------------------+

```

##### H5) 사용자 스레드와 데몬스레드
- 사용자 스레드: 애플리케이션의 주요 작업을 수행하며, 모든 사용자 스레드가 종료되어야만 JVM이 종료된다.
- 데몬 스레드: 백그라운드 작업을 수행하며, 모든 사용자 스레드가 종료되면 JVM에 의해 강제로 종료된다. (예: 가비지 컬렉션, 로그기록, 메모리 관리 등)

##### H5) 모든 스레드 종료
**사용자 스레드 종료**
- JVM은 모든 사용자 스레드가 종료될 때까지 기다리거나, `System.exit()` 호출 시 모든 사용자 스레드를 강제 종료한다.
- 사용자 스레드가 종료되지 않으면, JVM은 무한정 대기할 수 있다.
  **데몬 스레드 종료**
- 모든 사용자 스레드가 종료된 후, 남아있는 데몬 스레드를 종료한다.
- 데몬 스레드는 자동으로 종료되므로 별도의 종료작업이 필요하지 않다.
  **리소스 해제**
- JVM은 모든 스레드를 종료한 후, 할당된 메모리와 시스템 자원을 해제한다.
- JVM이 사용하는 네이티브 리소스(예: 파일핸들, 네트워크 소켓 등)도 해제된다.
  **JVM 종료**
- 모든 종료 작업이 완료되면, JVM은 종료상태를 운영체제에 전달하고 프로세스를 종료한다.
- 운영체제는 JVM 프로세스의 종료를 처리하고, 프로세스의 모든 자원을 해제한다.


> [!question] CPU 스케줄러의 알고리즘
> - **스케줄러는 프로세스가 아닌 스레드를 기반으로 알고리즘을 선택합니다.**
> - **스케줄링 알고리즘:** Round Robin, Priority Scheduling, Multilevel Queue Scheduling 등.

### H3) STW 하면 애플리케이션 응답시간이 지연되는게 똑같은데 애플리케이션 스레드로 GC를 돌리지 않는 이유?
##### H5) 동기화와 일관성
- 메모리 일관성 유지: 가비지 컬렉션 작업은 힙 메모리 전체의 상태를 검사하고 수정하는 작업이 포함된다. 여러 애플리케이션 스레드가 동시에 이 작업을 수행하면, 메모리 상태의 일관성을 유지하기 어려워진다.
- 경합 방지: 애플리케이션 스레드가 GC작업을 수행하면, 객체 그래프를 탐색하고 참조를 업데이트하는 동안 경합이 발생할 수 있다. 이는 메모리 일관성문제와 성능저하를 초래할 수 있다.
##### H5) 성능 최적화
- 전문화된 최적화: GC 스레드는 가비지 컬렉션 작업을 효율적으로 수행하기 위해 최적화된 알고리즘과 데이터 구조를 사용한다. 이러한 최적화는 애플리케이션 스레드가 처리하기에는 복잡하고 비용도 많이 드는 작업니다.
- 병렬처리 : 많은 현대 JVM은 병렬 또는 동시 GC 알고리즘을 사용하여 여러 GC 스레드가 동시에 작업을 수행한다. 이는 가비지 컬렉션의 속도를 크게 향상시킨다.
##### H5) 애플리케이션 성능 보호
- 응답시간 보장: 애플리케이션 스레드가 GC 작업을 수행하면, 응답시간과 전체 애플리케이션 성능이 저하될 수 있다. GC 스레드가 별도로 존재하면, 애플리케이션 스레드는 가비지 컬렉션 작업에서 분리되어 주요 로직을 실행할 수 있다.
- 작업 분리: GC 작업과 애플리케이션 로직을 분리함으로써, 각각의 작업을 독립적으로 최적화하고 관리할 수 있다. 이는 시스템 안정성과 성능을 유지하는데 중요하다.
##### H5) GC 알고리즘 복잡성
- 복잡한 작업 관리: GC 알고리즘은 메모리 관리, 객체 이동, 참조 업데이트, 메모리 압축 등을 포함하는 복잡한 작업이다. 애플리케이션 스레드가 이러한 작업을 수행하면, 애플리케이션 주요 로직과 메모리 관리 작업이 혼합되어 복잡성이 증가하낟.
- 알고리즘 전문성 : GC 스레드는 이러한 복잡한 작업을 수행하기 위해 전문화되어 있으며, GC 알고리즘의 최적화를 최대한 활용할 수 있다.

> [!tip]
> 한번 더 생각해보고 정리해보면 좋을 것 같다. 스레드의 존재 이유와 애플리케이션 스레드에 적용하려고 하면 어떤일이 일어날지


### H3) STW 이벤트로 GC 스레드가 애플리케이션 스레드를 멈추는 과정

**1. STW 이벤트 트리거**
- GC 스레드는 STW 이벤트를 트리거하여 모든 애플리케이션 스레드를 중단시킬 준비를 합니다. 이는 주기적인 가비지 컬렉션 타이머가 만료되거나 메모리 부족 상황이 발생할 때 이루어집니다.
  **2. 애플리케이션 스레드 중지**
- VM은 모든 애플리케이션 스레드가 안전한 지점(safe point)에 도달할 때까지 기다린 후, 모든 스레드의 실행을 일시 중지시킵니다. 안전한 지점은 GC가 메모리를 일관성 있게 검사하고 조작할 수 있도록 하는 지점입니다.
    - 안전한 지점 요청: JVM은 모든 스레드에게 안전한 지점에 도달하라는 요청을 보냅니다.
    - 안전한 지점 도달: 각 스레드는 안전한 지점에 도달하면 중단됩니다.
    - 안전한 지점(Safe Point)
        - 메서드 호출 시작 또는 호출 종료
        - 루프의 끝부분
        - 객체 할당

- 모든 애플리케이션 스레드가 안전한 지점에 도달하면, JVM은 이들 스레드를 중지한다.
- 스레드가 안전한 지점에 도달하지 못한 경우, JVM은 해당 스레드가 안전한 지점에 도달할 때까지 대기한다.
  **3. GC 작업 수행**
- 모든 애플리케이션 스레드가 중지되면, GC 스레드가 가비지 컬렉션 작업을 수행한다.
  **4. 애플리케이션 재개**
- 가비지 컬렉션 작업이 완료되면, GC 스레드는 중단된 애플리케이션 스레드를 다시 실행하도록 합니다. 각 스레드는 중단된 지점에서 실행을 재개합니다.


