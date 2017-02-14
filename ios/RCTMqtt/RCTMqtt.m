//
//  RCTMqtt.m
//  RCTMqtt
//
//  Created by Tuan PM on 2/2/16.
//  Copyright © 2016 Tuan PM. All rights reserved.
//

#import <React/RCTBridgeModule.h>
#import <React/RCTLog.h>
#import <React/RCTUtils.h>
#import <React/RCTEventDispatcher.h>

#import "RCTMqtt.h"
#import "Mqtt.h"



@interface RCTMqtt ()
@property NSMutableDictionary *clients;
@end


@implementation RCTMqtt


-(int)getRandomNumberBetween:(int)from to:(int)to {
    
    return (int)from + arc4random() % (to-from+1);
}

RCT_EXPORT_MODULE();


- (instancetype)init
{
    if ((self = [super init])) {
        _clients = [[NSMutableDictionary alloc] init];
    }
    return self;
    
}

- (NSArray<NSString *> *)supportedEvents {
    return @[ @"mqtt_events" ];
}

RCT_EXPORT_METHOD(createClient:(NSDictionary *) options
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    
    int clientRef = [self getRandomNumberBetween:1000 to:9999];
    
    Mqtt *client = [[Mqtt alloc] initWithEmitter:self
                                         options:options
                                       clientRef:clientRef];
    
    [[self clients] setObject:client forKey:[NSNumber numberWithInt:clientRef]];
    resolve([NSNumber numberWithInt:clientRef]);
    
}

RCT_EXPORT_METHOD(removeClient:(nonnull NSNumber *) clientRef) {
    [[self clients] removeObjectForKey:clientRef];
}

RCT_EXPORT_METHOD(connect:(nonnull NSNumber *) clientRef) {
    [[[self clients] objectForKey:clientRef] connect];
}


RCT_EXPORT_METHOD(disconnect:(nonnull NSNumber *) clientRef) {
    [[[self clients] objectForKey:clientRef] disconnect];
}

RCT_EXPORT_METHOD(subscribe:(nonnull NSNumber *) clientRef topic:(NSString *)topic qos:(nonnull NSNumber *)qos) {
    [[[self clients] objectForKey:clientRef] subscribe:topic qos:qos];
}

RCT_EXPORT_METHOD(unsubscribe:(nonnull NSNumber *) clientRef topic:(NSString *)topic) {
    [[[self clients] objectForKey:clientRef] unsubscribe:topic];
}

RCT_EXPORT_METHOD(publish:(nonnull NSNumber *) clientRef topic:(NSString *)topic data:(NSString*)data qos:(nonnull NSNumber *)qos retain:(BOOL)retain) {
    [[[self clients] objectForKey:clientRef] publish:topic
                                                data:[data dataUsingEncoding:NSUTF8StringEncoding]
                                                 qos:qos
                                              retain:retain];
    
}

- (void)dealloc
{
}

@end


