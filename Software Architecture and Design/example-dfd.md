# Data Flow Diagram for Online Food Delivery System

A Data Flow Diagram (DFD) is a graphical representation that shows the flow of data through a system. This document presents a detailed DFD for an Online Food Delivery System, starting from the context level (Level 0) and progressively refining into more detailed levels.

## Level 0 DFD (Context Diagram)

The context diagram shows the entire system as a single process and its interaction with external entities.

```mermaid
flowchart TD
    Customer([Customer]) -- "Orders, Payments, Feedback" --> System((Online Food Delivery System))
    System -- "Order Confirmation, Delivery Updates, Receipts" --> Customer
    
    Restaurant([Restaurant]) -- "Menu Updates, Order Confirmations" --> System
    System -- "Order Details, Payment Info" --> Restaurant
    
    DeliveryPartner([Delivery Partner]) -- "Location Updates, Delivery Status" --> System
    System -- "Delivery Assignments, Route Info" --> DeliveryPartner
    
    PaymentGateway([Payment Gateway]) -- "Payment Confirmations" --> System
    System -- "Payment Requests" --> PaymentGateway
```

## Level 1 DFD

Level 1 breaks down the main system into its major processes and shows how data flows between them.

```mermaid
flowchart TD
    %% External Entities
    Customer([Customer])
    Restaurant([Restaurant]) 
    DeliveryPartner([Delivery Partner])
    PaymentGateway([Payment Gateway])
    
    %% Main Processes
    P1((1. User Management))
    P2((2. Order Processing))
    P3((3. Payment Handling))
    P4((4. Restaurant Management))
    P5((5. Delivery Management))
    
    %% Data Stores
    DS1[(User Data)]
    DS2[(Order Data)]
    DS3[(Payment Data)]
    DS4[(Restaurant Data)]
    DS5[(Delivery Data)]
    
    %% Customer Flows
    Customer -- "Registration, Login, Profile Updates" --> P1
    P1 -- "Account Info, Authentication" --> Customer
    Customer -- "Browse Menu, Place Order" --> P2
    P2 -- "Order Confirmation, Status Updates" --> Customer
    Customer -- "Payment Info" --> P3
    P3 -- "Payment Confirmation, Receipt" --> Customer
    
    %% Restaurant Flows
    Restaurant -- "Menu Updates, Restaurant Info" --> P4
    P4 -- "Dashboard Access, Analytics" --> Restaurant
    P4 -- "New Orders" --> Restaurant
    Restaurant -- "Order Acceptance/Rejection" --> P2
    
    %% Delivery Partner Flows
    DeliveryPartner -- "Availability, Location" --> P5
    P5 -- "Delivery Assignments, Navigation" --> DeliveryPartner
    DeliveryPartner -- "Delivery Status Updates" --> P5
    
    %% Payment Gateway Flows
    P3 -- "Process Payment" --> PaymentGateway
    PaymentGateway -- "Payment Status" --> P3
    
    %% Internal Data Flows
    P1 <--> DS1
    P2 <--> DS2
    P3 <--> DS3
    P4 <--> DS4
    P5 <--> DS5
    
    P2 -- "Order Details" --> P3
    P2 -- "Order For Preparation" --> P4
    P2 -- "Delivery Request" --> P5
    P3 -- "Payment Status" --> P2
    P5 -- "Delivery Status" --> P2
```

## Level 2 DFD: Order Processing (Process 2)

Level 2 expands on the Order Processing component from Level 1, showing its sub-processes in more detail.

```mermaid
flowchart TD
    %% External Entities
    Customer([Customer])
    Restaurant([Restaurant])
    
    %% Related Level 1 Processes
    P1((1. User Management))
    P3((3. Payment Handling))
    P5((5. Delivery Management))
    
    %% Level 2 Processes for Order Processing
    P2_1((2.1 Browse Menu))
    P2_2((2.2 Create Order))
    P2_3((2.3 Process Order))
    P2_4((2.4 Track Order))
    P2_5((2.5 Order Notification))
    
    %% Data Stores
    DS1[(User Data)]
    DS2[(Order Data)]
    DS4[(Restaurant Data)]
    
    %% Customer Flows
    Customer -- "Browse Restaurants, Menu Items" --> P2_1
    P2_1 -- "Menu Options, Pricing" --> Customer
    Customer -- "Item Selection, Order Details" --> P2_2
    P2_2 -- "Order Summary, Total" --> Customer
    Customer -- "Order Confirmation" --> P2_3
    P2_5 -- "Status Notifications" --> Customer
    Customer -- "Order Status Request" --> P2_4
    P2_4 -- "Real-time Updates" --> Customer
    
    %% Restaurant Flows
    P2_3 -- "New Order Alert" --> Restaurant
    Restaurant -- "Order Accepted/Rejected" --> P2_3
    
    %% Data Store Interactions
    P2_1 <--> DS4
    P2_2 <--> DS1
    P2_2 --> DS2
    P2_3 <--> DS2
    P2_4 <--> DS2
    
    %% Interactions with other Level 1 Processes
    P1 -- "User Preferences, Address" --> P2_2
    P2_3 -- "Payment Required" --> P3
    P3 -- "Payment Confirmed" --> P2_3
    P2_3 -- "Order Ready for Delivery" --> P5
    P5 -- "Delivery Updates" --> P2_4
    P2_4 -- "Status Changes" --> P2_5
```

## Level 3 DFD: Order Notification (Process 2.5)

Level 3 further expands on the Order Notification process from Level 2, showing its detailed implementation.

```mermaid
flowchart TD
    %% External Entities
    Customer([Customer])
    Restaurant([Restaurant])
    DeliveryPartner([Delivery Partner])
    
    %% Related Level 2 Processes
    P2_3((2.3 Process Order))
    P2_4((2.4 Track Order))
    
    %% Level 3 Processes for Order Notification
    P2_5_1((2.5.1 Generate Notification))
    P2_5_2((2.5.2 Select Notification Channel))
    P2_5_3((2.5.3 Format Message))
    P2_5_4((2.5.4 Send Notification))
    P2_5_5((2.5.5 Log Notification))
    
    %% Data Stores
    DS1[(User Data)]
    DS2[(Order Data)]
    DS6[(Notification Log)]
    
    %% Process Flows
    P2_3 -- "Status Change Event" --> P2_5_1
    P2_4 -- "Status Change Event" --> P2_5_1
    
    P2_5_1 -- "Notification Event" --> P2_5_2
    P2_5_2 -- "Channel Selected" --> P2_5_3
    P2_5_3 -- "Formatted Message" --> P2_5_4
    P2_5_4 -- "Sent Notification" --> P2_5_5
    
    %% Data Store Interactions
    P2_5_1 <--> DS2
    P2_5_2 <--> DS1
    P2_5_5 --> DS6
    
    %% External Entity Interactions
    P2_5_4 -- "App Notification" --> Customer
    P2_5_4 -- "SMS" --> Customer
    P2_5_4 -- "Email" --> Customer
    P2_5_4 -- "Order Alert" --> Restaurant
    P2_5_4 -- "Pickup Alert" --> DeliveryPartner
```

## DFD Components Explanation

### External Entities
- **Customer**: End-users who order food through the platform
- **Restaurant**: Food providers who list their menu and fulfill orders
- **Delivery Partner**: Personnel responsible for picking up and delivering orders
- **Payment Gateway**: Third-party service handling financial transactions

### Main Processes
1. **User Management**: Handles user registration, authentication, and profile management
2. **Order Processing**: Manages the creation, tracking, and fulfillment of orders
3. **Payment Handling**: Processes payments, refunds, and maintains transaction records
4. **Restaurant Management**: Manages restaurant profiles, menus, and order acceptance
5. **Delivery Management**: Assigns delivery partners and tracks delivery progress

### Data Stores
1. **User Data**: Stores customer profiles, preferences, and authentication information
2. **Order Data**: Contains all order details, status, and history
3. **Payment Data**: Records transaction information and payment status
4. **Restaurant Data**: Stores restaurant profiles, menus, and operational information
5. **Delivery Data**: Contains delivery partner information and delivery records
6. **Notification Log**: Records of all notifications sent through the system

### Key Data Flows

#### Level 1 Key Flows:
- **Customer to Order Processing**: Customer places an order with specific requirements
- **Order Processing to Payment Handling**: Order details sent for payment processing
- **Order Processing to Delivery Management**: Fulfilled orders passed for delivery assignment
- **Restaurant to Restaurant Management**: Menu and availability updates

#### Level 2 Key Flows:
- **Browse Menu to Create Order**: Selected items passed to order creation
- **Create Order to Process Order**: Complete order transferred for processing
- **Process Order to Payment Handling**: Payment request for the order total
- **Track Order to Order Notification**: Status changes triggering notifications

#### Level 3 Key Flows:
- **Generate Notification to Select Channel**: Notification event determining appropriate channels
- **Format Message to Send Notification**: Properly formatted messages ready for distribution
- **Send Notification to External Entities**: Alerts sent to customers, restaurants, and delivery partners
